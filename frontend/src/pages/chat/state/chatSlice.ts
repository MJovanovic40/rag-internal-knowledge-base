import type { RootState } from "@/store";
import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { ChatHistoryResponse, ChatResponse } from "../api/ChatApi";

export interface Message {
  content: string;
  type: "ASSISTANT" | "USER" | "SYSTEM" | "TOOL";
  createdAt: string;
}

export interface CurrentChat {
  id: string;
  title: string;
}

export interface ChatState {
  currentMessages: Message[];
  current: CurrentChat;
  conversations: Conversation[];
}

export interface Conversation {
  id: string;
  title: string;
  userId: string;
  createdAt: string;
}

const initialState: ChatState = {
  currentMessages: [],
  current: {
    id: "",
    title: "New Chat",
  },
  conversations: [],
};

export const messagesSlice = createSlice({
  name: "chat",
  initialState,
  reducers: {
    setCurrentMessages: (state, action: PayloadAction<ChatHistoryResponse[]>) => {
      state.currentMessages.splice(0);
      action.payload.forEach((message) => {
        state.currentMessages.push({
          content: message.message,
          type: message.messageType,
          createdAt: message.createdAt,
        });
      });
    },
    addMessage: (state, action: PayloadAction<ChatHistoryResponse>) => {
      state.currentMessages.push({
        content: action.payload.message,
        type: action.payload.messageType,
        createdAt: action.payload.createdAt,
      });
    },
    setCurrentChat: (state, action: PayloadAction<CurrentChat>) => {
      state.current.id = action.payload.id;
      state.current.title = action.payload.title;
    },
    setConversations: (state, action: PayloadAction<ChatResponse[]>) => {
      state.conversations.splice(0);
      action.payload.forEach((conversation) => {
        state.conversations.push(conversation);
      });
    },
    addConversation: (state, action: PayloadAction<Conversation>) => {
      state.conversations.splice(0, 0, action.payload);
    },
  },
});

export const { setCurrentMessages, addMessage, setCurrentChat, setConversations, addConversation } =
  messagesSlice.actions;

export const selectMessages = (state: RootState) => state.chat.currentMessages;
export const selectCurrentChat = (state: RootState) => state.chat.current;

export default messagesSlice.reducer;
