import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { cn } from "@/lib/utils";
import { register } from "@/pages/authentication/api/AuthenticationApi";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router";
import SpinnerCircle from "./customized/spinner/spinner-02";
import { Banner, BannerTitle } from "./ui/shadcn-io/banner";

export function RegisterForm({ className, ...props }: React.ComponentProps<"div">) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [working, setWorking] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("auth")) navigate("/");
  }, [navigate]);

  const registerMutation = useMutation({
    mutationFn: () => register(email, password),
    onSuccess: () => {
      navigate("/login");
    },
    onError: () => {
      setWorking(false);
      setError("Invalid email or password or user exists.");
      setTimeout(() => setError(""), 5000);
    },
  });

  const onSubmit = (e: FormEvent) => {
    e.preventDefault();
    setWorking(true);
    registerMutation.mutate();
  };

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle>Register</CardTitle>
          <CardDescription>Enter your email below to register</CardDescription>
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
                  {working ? <SpinnerCircle /> : "Register"}
                </Button>
              </div>
            </div>
            <div className="mt-4 text-center text-sm">
              Already have an account? <Link to={"/login"}>Login</Link>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
