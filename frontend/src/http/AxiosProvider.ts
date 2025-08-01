import axios from "axios";

export const axiosClient = axios.create({
  headers: { Authorization: `Bearer ${localStorage.getItem("auth")}` },
});

axiosClient.interceptors.response.use(undefined, async (error) => {
  if (error.response?.status === 401) {
    localStorage.removeItem("auth");
    location.href = "/login";
  }
  throw error;
});
