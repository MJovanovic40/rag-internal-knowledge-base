import { Avatar, AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";

export interface ChatMessageProps {
  type: "ASSISTANT" | "USER" | "SYSTEM" | "TOOL";
  text: string;
}

export default function ChatMessage(props: ChatMessageProps) {
  const userStyles =
    "bg-neutral-300 text-black p-3 rounded-2xl max-w-[60%] float-end min-w-1/3 wrap-break-word overflow-hidden";
  const assistantStyles =
    "bg-[#262626] text-white p-3 rounded-2xl max-w-[60%] min-w-1/3 wrap-break-word overflow-hidden";

  return (
    <>
      {props.type === "ASSISTANT" ? (
        <div className="flex items-end-safe gap-2">
          <Avatar className="w-12 h-12">
            <AvatarImage
              className="rounded-4xl"
              src="https://api.dicebear.com/9.x/initials/svg?seed=AI&backgroundColor=262626"
            />
            <AvatarFallback>AI</AvatarFallback>
          </Avatar>
          <div className={assistantStyles}>
            {props.text.split("\n").map((str, i) => {
              if (str) return <p key={`p-${i}`}>{str}</p>;
              else return <br key={`br-${i}`} />;
            })}
          </div>
        </div>
      ) : (
        <div className="flex items-end justify-end-safe gap-2">
          <div className={userStyles}>
            {props.text.split("\n").map((str, i) => {
              if (str) return <p key={`p-${i}`}>{str}</p>;
              else return <br key={`br-${i}`} />;
            })}
          </div>
          <Avatar className="w-12 h-12">
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
