import jwt from "jsonwebtoken";

export const getUserId = (): string | null => {
  const token = localStorage.getItem("auth");

  if (!token) return null;

  const payload = jwt.decode(token) as jwt.JwtPayload;

  return payload.sub ?? null;
};
