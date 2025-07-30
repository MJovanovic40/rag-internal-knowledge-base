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
  name: string,
  email: string,
  password: string
): Promise<AxiosResponse<RegisterResponse>> => {
  const payload = {
    name,
    email,
    password,
  };

  return await axios.post("/api/v1/auth/register", payload);
};
