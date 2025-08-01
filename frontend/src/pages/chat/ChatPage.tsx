import ChatMessage from "@/components/chat/chat-message";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { useAppSelector } from "@/hooks";
import { ArrowUp } from "lucide-react";
import { useEffect, useRef, useState, type FormEvent } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router";
import { getChatHisotry, sendMessage, type ChatHistoryResponse } from "./api/ChatApi";
import { addConversation, setCurrentChat } from "./state/chatSlice";

export default function ChatPage() {
  const chatRef = useRef<HTMLDivElement>(null);
  const chatEndRef = useRef<HTMLSpanElement>(null);
  const formRef = useRef<HTMLFormElement>(null);

  const [chatWidth, setChatWidth] = useState(0);
  const [messages, setMessages] = useState<ChatHistoryResponse[]>([]);
  const [input, setInput] = useState<string>("");
  const [streaming, setStreaming] = useState<boolean>(false);

  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const conversations = useAppSelector((state) => state.chat.conversations);

  const getConversationById = (id: string) => {
    for (let i = 0; i < conversations.length; i++) {
      if (conversations[i].id === id) return conversations[i];
    }
    return null;
  };

  const getChats = async (chatId: string) => {
    const messages = await getChatHisotry(chatId);
    setMessages(messages);
  };

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await newMessage();
  };

  const scrollToBottom = () => {
    chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth",
    });
  };

  const newMessage = async () => {
    setStreaming(true);
    const chatId = searchParams.get("chat");

    // Clear input
    const userMessage: ChatHistoryResponse = {
      id: -1,
      createdAt: "",
      message: input,
      messageType: "USER",
    };

    setInput("");
    setMessages((prev) => [...prev, userMessage]);

    // Insert an empty assistant message placeholder
    const assistantId = -2;
    setMessages((prev) => [
      ...prev,
      {
        id: assistantId,
        createdAt: "",
        message: "",
        messageType: "ASSISTANT",
      },
    ]);

    try {
      const reader = await sendMessage(chatId, input);
      const decoder = new TextDecoder();
      let buffer = "";

      while (true) {
        const { done, value } = await reader.read();
        if (done) {
          break;
        }
        buffer += decoder.decode(value, { stream: true });
        const lines = buffer.split("\n");
        buffer = lines.pop()!;

        for (let line of lines) {
          if (!line.trim()) continue;

          try {
            // If using format like "data: {json...}", strip "data: "
            line = line.substring(5);
            const json = JSON.parse(line);
            const token = json.message;
            const newChatId = json.chatId;

            if (token === "END") {
              setStreaming(false);
              dispatch(
                addConversation({
                  userId: "",
                  createdAt: "",
                  id: newChatId,
                  title: json.title,
                })
              );
              if (newChatId != chatId) navigate(`/?chat=${newChatId}`);
            }

            // Update assistant message atomically
            setMessages((prev) => {
              const newMessages = [...prev];
              const lastIndex = newMessages.length - 1;

              // Only update if it's an assistant message
              if (newMessages[lastIndex]?.messageType === "ASSISTANT") {
                newMessages[lastIndex] = {
                  ...newMessages[lastIndex],
                  message: newMessages[lastIndex].message + token,
                };
              }

              return newMessages;
            });
          } catch (e) {
            console.warn("Invalid JSON chunk:", line, e);
          }
        }
      }
    } catch (err) {
      console.error("Streaming error:", err);
      setMessages((prev) =>
        prev.map((msg) =>
          msg.id === assistantId ? { ...msg, message: "[Error during streaming]" } : msg
        )
      );
    }
  };

  useEffect(() => {
    const el = chatRef.current;
    if (!el) return;

    const observer = new ResizeObserver(() => {
      setChatWidth(el.clientWidth);
    });

    observer.observe(el);

    return () => observer.disconnect();
  });

  useEffect(() => {
    const chatId = searchParams.get("chat");

    if (!chatId) {
      dispatch(
        setCurrentChat({
          id: "",
          title: "New Chat",
        })
      );
      return;
    }

    getChats(chatId);

    const conversation = getConversationById(chatId);

    if (conversation != null)
      dispatch(
        setCurrentChat({
          id: conversation.id,
          title: conversation.title,
        })
      );

    scrollToTop();

    return () => {
      setMessages([]);
      scrollToTop();
    };
  }, [searchParams]);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  return (
    <div ref={chatRef} className="w-[90%] xl:w-[60%] mt-22 ">
      <div className="flex flex-col gap-5">
        {messages &&
          messages.map((message, i) => {
            return (
              <ChatMessage key={`message-${i}`} type={message.messageType} text={message.message} />
            );
          })}
        <span ref={chatEndRef} className="h-20"></span>
      </div>
      <div className="w-full h-20 bg-neutral-900 fixed bottom-0 ">
        <form ref={formRef} style={{ width: chatWidth }} onSubmit={onSubmit}>
          <div className="flex justify-center items-center gap-3 pb-3 w-full">
            <Textarea
              value={input}
              placeholder="Type your message here..."
              onChange={(e) => setInput(e.target.value)}
              disabled={streaming}
              onKeyDown={async (e) =>
                e.key === "Enter" && !e.shiftKey && input.length > 0 && (await newMessage())
              }
            ></Textarea>
            <Button disabled={!input || streaming} type="submit" className="rounded-2xl w-12 h-12">
              <ArrowUp />
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
