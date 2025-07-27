import ChatMessage from "@/components/chat/chat-message";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { ArrowUp } from "lucide-react";

export default function ChatPage() {
  return (
    <div className="flex h-full w-full items-center justify-center">
      <div className="flex flex-col justify-between w-[90%] md:w-3/4 xl:w-1/2 h-[80%] bg-neutral-900 rounded-3xl">
        <div className="overflow-y-auto">
          <ChatMessage type="user">
            Lorem Ipsum is simply dummy text of the printing and typesetting
          </ChatMessage>
          <ChatMessage type="assistant">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            hasLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem
            Ipsum has
          </ChatMessage>
          <ChatMessage type="user">
            Lorem Ipsum is simply dummy text of the printing and typesetting
          </ChatMessage>
          <ChatMessage type="assistant">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            hasLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem
            Ipsum has
          </ChatMessage>
          <ChatMessage type="user">
            Lorem Ipsum is simply dummy text of the printing and typesetting
          </ChatMessage>
          <ChatMessage type="assistant">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            hasLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem
            Ipsum has
          </ChatMessage>
          <ChatMessage type="user">
            Lorem Ipsum is simply dummy text of the printing and typesetting
          </ChatMessage>
          <ChatMessage type="assistant">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            hasLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem
            Ipsum has
          </ChatMessage>
          <ChatMessage type="user">
            Lorem Ipsum is simply dummy text of the printing and typesetting
          </ChatMessage>
          <ChatMessage type="assistant">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            hasLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem
            Ipsum has
          </ChatMessage>
          <ChatMessage type="user">
            Lorem Ipsum is simply dummy text of the printing and typesetting
          </ChatMessage>
          <ChatMessage type="assistant">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            hasLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem
            Ipsum has
          </ChatMessage>
        </div>

        <form>
          <div className="flex justify-center items-end gap-3 p-5">
            <Textarea className="w-[90%]" placeholder="Type your message here..."></Textarea>
            <Button className="rounded-4xl">
              <ArrowUp />
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
