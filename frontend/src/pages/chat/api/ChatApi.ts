import type { AxiosResponse } from "axios";
import axios from "axios";

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

export const getChats = async (): Promise<AxiosResponse<ChatResponse[]>> => {
  return await axios.get(`/api/v1/chats/user`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
  });
};

export const getChatHisotry = async (
  chatId: string
): Promise<AxiosResponse<ChatHistoryResponse[]>> => {
  return await axios.get(`/api/v1/chats/history/${chatId}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
  });
};

export const sendMessage = async (
  chatId: string,
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
