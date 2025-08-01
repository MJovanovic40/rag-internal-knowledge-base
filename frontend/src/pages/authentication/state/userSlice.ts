import type { RootState } from "@/store";
import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { User } from "../api/UserApi";

export interface UserState {
  name: string;
  email: string;
  role: "USER" | "ADMIN";
}

const initialState: UserState = {
  name: "",
  email: "",
  role: "USER",
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUser: (state, action: PayloadAction<User>) => {
      state.name = action.payload.name;
      state.email = action.payload.email;
      state.role = action.payload.role;
    },
  },
});

export const { setUser } = userSlice.actions;

export const selectUser = (state: RootState) => state.user;

export default userSlice.reducer;
