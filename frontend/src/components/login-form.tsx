import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useAppDispatch } from "@/hooks";
import { cn } from "@/lib/utils";
import { login } from "@/pages/authentication/api/AuthenticationApi";
import { getUserDetails } from "@/pages/authentication/api/UserApi";
import { setUser } from "@/pages/authentication/state/userSlice";
import type { ErrorResponse } from "@/types/ErrorResponse";
import { useMutation } from "@tanstack/react-query";
import type { AxiosError } from "axios";
import { useEffect, useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router";
import SpinnerCircle from "./customized/spinner/spinner-02";
import { Banner, BannerTitle } from "./ui/shadcn-io/banner";

export function LoginForm({ className, ...props }: React.ComponentProps<"div">) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [working, setWorking] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const dispatch = useAppDispatch();

  useEffect(() => {
    if (localStorage.getItem("auth")) navigate("/");
  }, [navigate]);

  const loginMutation = useMutation({
    mutationFn: () => login(email, password),
    onSuccess: async (response) => {
      localStorage.setItem("auth", response.data.jwt);

      const user = await getUserDetails();
      dispatch(setUser(user));

      // navigate("/");
      location.href = "/";
    },
    onError: (error: AxiosError<ErrorResponse>) => {
      setWorking(false);
      setError(error.response!.data.message);
      setTimeout(() => setError(""), 5000);
    },
  });

  const onSubmit = (e: FormEvent) => {
    e.preventDefault();
    setWorking(true);
    loginMutation.mutate();
  };

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle>Login to your account</CardTitle>
          <CardDescription>Enter your email below to login to your account</CardDescription>
          {error && (
            <Banner>
              <BannerTitle className="text-center">{error}</BannerTitle>
            </Banner>
          )}
        </CardHeader>
        <CardContent>
          <form onSubmit={onSubmit}>
            <div className="flex flex-col gap-6">
              <div className="grid gap-3">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="m@example.com"
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
              <div className="grid gap-3">
                <div className="flex items-center">
                  <Label htmlFor="password">Password</Label>
                </div>
                <Input
                  id="password"
                  type="password"
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
              <div className="flex flex-col gap-3">
                <Button type="submit" className="w-full">
                  {working ? <SpinnerCircle /> : "Login"}
                </Button>
              </div>
            </div>
            <div className="mt-4 text-center text-sm">
              Don&apos;t have an account? <Link to={"/register"}>Sign up</Link>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
