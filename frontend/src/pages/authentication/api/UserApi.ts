import type { AxiosResponse } from "axios";
import axios from "axios";

export type User = {
  id: string;
  name: string;
  email: string;
  role: "USER" | "ADMIN";
  createdAt: string;
};

export const getUserDetails = async (): Promise<AxiosResponse<User>> => {
  return axios.get(`/api/v1/users`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
  });
};
