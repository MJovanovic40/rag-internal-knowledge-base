import { jwtDecode } from "jwt-decode";

export const getUserId = (): string | null => {
  const token = localStorage.getItem("auth");
  if (!token) return null;
  return jwtDecode(token).sub ?? null;
};
