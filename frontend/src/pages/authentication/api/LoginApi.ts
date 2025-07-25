import axios, { type AxiosResponse } from "axios";

type LoginResponse = {
  jwt: string;
};

type RegisterResponse = {
  id: string;
  email: string;
  role: string;
  createdAt: string;
};

export const login = async (
  email: string,
  password: string
): Promise<AxiosResponse<LoginResponse>> => {
  const payload = {
    email,
    password,
  };

  return await axios.post("/api/v1/auth/login", payload);
};

export const register = async (
  email: string,
  password: string
): Promise<AxiosResponse<RegisterResponse>> => {
  const payload = {
    email,
    password,
  };

  return await axios.post("/api/v1/register", payload);
};
