import { axiosClient } from "@/http/AxiosProvider";

export type User = {
  id: string;
  name: string;
  email: string;
  role: "USER" | "ADMIN";
  createdAt: string;
};

export const getUserDetails = async (): Promise<User> => {
  const resp = await axiosClient.get(`/api/v1/users`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("auth")}`,
    },
  });
  return resp.data;
};
