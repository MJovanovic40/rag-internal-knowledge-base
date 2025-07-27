import { getUserId } from "@/utils/JwtUtils";
import type { AxiosResponse } from "axios";
import axios from "axios";

type ChatResponse = {
  id: string;
  title: string;
  userId: string;
  createdAt: string;
};

type ChatHistoryResponse = {
  id: number;
  message: string;
  messageType: "USER" | "ASSISTANT" | "SYSTEM" | "TOOL";
  createdAt: string;
};

export const getChats = async (): Promise<AxiosResponse<ChatResponse[]>> => {
  const userId = getUserId();

  return await axios.get(`/api/v1/chats/user/${userId}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
  });
};

export const getChatHisotry = async (
  chatId: string
): Promise<AxiosResponse<ChatHistoryResponse>> => {
  return await axios.get(`/api/v1/chats/history/${chatId}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
  });
};

export const sendMessage = async (
  chatId: string,
  prompt: string
): Promise<ReadableStreamDefaultReader<Uint8Array>> => {
  const response = await fetch(`/api/v1/chat`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ chatId, prompt }),
  });

  if (!response.ok || !response.body) {
    throw new Error("Failed to open response stream");
  }

  return response.body.getReader();
};
