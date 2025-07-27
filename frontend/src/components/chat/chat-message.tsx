import { Avatar, AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";
import type React from "react";

interface ChatMessageProps {
  children: React.ReactNode;
  type: "assistant" | "user";
}

export default function ChatMessage(props: ChatMessageProps) {
  const userStyles =
    "bg-neutral-300 text-black p-3 rounded-2xl max-w-[60%] float-end min-w-1/3 m-4 ml-2 wrap-break-word overflow-hidden";
  const assistantStyles =
    "bg-neutral-950 text-white p-3 rounded-2xl max-w-[60%] min-w-1/3 m-4 ml-2 wrap-break-word overflow-hidden";

  return (
    <>
      {props.type === "assistant" ? (
        <div className="flex w-full items-end pl-2">
          <Avatar className="w-12 h-12 mb-4">
            <AvatarImage
              className="rounded-4xl"
              src="https://api.dicebear.com/9.x/initials/svg?seed=AI&backgroundColor=0a0a0a"
            />
            <AvatarFallback>AI</AvatarFallback>
          </Avatar>
          <div className={assistantStyles}>{props.children}</div>
        </div>
      ) : (
        <div className="flex w-full items-end justify-end-safe pr-2">
          <div className={userStyles}>{props.children}</div>
          <Avatar className="w-12 h-12 mb-4">
            <AvatarImage
              className="rounded-4xl"
              src="https://api.dicebear.com/9.x/initials/svg?seed=MJ&backgroundColor=d4d4d4&textColor=000000"
            />
            <AvatarFallback>MJ</AvatarFallback>
          </Avatar>
        </div>
      )}
    </>
  );
}
