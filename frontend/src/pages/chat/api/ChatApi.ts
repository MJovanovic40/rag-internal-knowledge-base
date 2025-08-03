import { axiosClient } from "@/http/AxiosProvider";

export type ChatResponse = {
  id: string;
  title: string;
  userId: string;
  createdAt: string;
};

export type ChatHistoryResponse = {
  id: number;
  message: string;
  messageType: "USER" | "ASSISTANT" | "SYSTEM" | "TOOL";
  createdAt: string;
};

export const getChats = async (): Promise<ChatResponse[]> => {
  return (await axiosClient.get(`/api/v1/chats/user`)).data;
};

export const getChatHisotry = async (chatId: string): Promise<ChatHistoryResponse[]> => {
  return (await axiosClient.get(`/api/v1/chats/history/${chatId}`)).data;
};

export const sendMessage = async (
  chatId: string | null,
  message: string
): Promise<ReadableStreamDefaultReader<Uint8Array>> => {
  const response = await fetch(`/api/v1/chats`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
    body: JSON.stringify({ chatId, message }),
  });

  if (!response.ok || !response.body) {
    throw new Error("Failed to open response stream");
  }

  return response.body.getReader();
};

export const getChat = async (chatId: string): Promise<ChatResponse> => {
  return (
    await axiosClient.get(`/api/v1/chats/${chatId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("auth")}`,
      },
    })
  ).data;
};
