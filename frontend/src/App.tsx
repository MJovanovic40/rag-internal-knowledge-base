import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Route, Routes } from "react-router";
import "./App.css";
import { ThemeProvider } from "./components/theme-provider";
import Layout from "./layouts/Layout";
import LoginPage from "./pages/authentication/LoginPage";
import RegisterPage from "./pages/authentication/RegisterPage";
import ChatPage from "./pages/chat/ChatPage";

const queryClient = new QueryClient();

export default function App() {
  return (
    <BrowserRouter>
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        <QueryClientProvider client={queryClient}>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/" element={<Layout pageComponent={<ChatPage />} showSidebar={true} />} />
          </Routes>
        </QueryClientProvider>
      </ThemeProvider>
    </BrowserRouter>
  );
}
