import ChatMessage from "@/components/chat/chat-message";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { ArrowUp } from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function ChatPage() {
  const chatRef = useRef<HTMLDivElement>(null);
  const [chatWidth, setChatWidth] = useState(0);

  useEffect(() => {
    const el = chatRef.current;
    if (!el) return;

    const observer = new ResizeObserver(() => {
      setChatWidth(el.clientWidth);
    });

    observer.observe(el);

    return () => observer.disconnect();
  });

  return (
    <div ref={chatRef} className="w-[90%] xl:w-[60%] mt-22">
      <div className="flex flex-col gap-5">
        <ChatMessage type="user">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been t
        </ChatMessage>
        <ChatMessage type="assistant">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been the industry's standard dummy text ever since the 1500s, when an unknown printer took
          a galley of type and scrambled it to make a type specimen book. It has survived not only
          five centuries, but also the leap into electronic typesetting, remaining essentially
          unchanged. It was popularise
        </ChatMessage>
        <ChatMessage type="user">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been t
        </ChatMessage>
        <ChatMessage type="assistant">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been the industry's standard dummy text ever since the 1500s, when an unknown printer took
          a galley of type and scrambled it to make a type specimen book. It has survived not only
          five centuries, but also the leap into electronic typesetting, remaining essentially
          unchanged. It was popularise
        </ChatMessage>
        <ChatMessage type="user">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been t
        </ChatMessage>
        <ChatMessage type="assistant">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been the industry's standard dummy text ever since the 1500s, when an unknown printer took
          a galley of type and scrambled it to make a type specimen book. It has survived not only
          five centuries, but also the leap into electronic typesetting, remaining essentially
          unchanged. It was popularise
        </ChatMessage>
        <ChatMessage type="user">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been t
        </ChatMessage>
        <ChatMessage type="assistant">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been the industry's standard dummy text ever since the 1500s, when an unknown printer took
          a galley of type and scrambled it to make a type specimen book. It has survived not only
          five centuries, but also the leap into electronic typesetting, remaining essentially
          unchanged. It was popularise
        </ChatMessage>
        <ChatMessage type="user">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been t
        </ChatMessage>
        <ChatMessage type="assistant">
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has
          been the industry's standard dummy text ever since the 1500s, when an unknown printer took
          a galley of type and scrambled it to make a type specimen book. It has survived not only
          five centuries, but also the leap into electronic typesetting, remaining essentially
          unchanged. It was popularise
        </ChatMessage>
        <span className="h-20"></span>
      </div>
      <div className="w-full h-20 bg-neutral-900 fixed bottom-0 ">
        <form style={{ width: chatWidth }}>
          <div className="flex justify-center items-center gap-3 pb-3 w-full">
            <Textarea placeholder="Type your message here..."></Textarea>
            <Button className="rounded-2xl w-12 h-12">
              <ArrowUp />
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
