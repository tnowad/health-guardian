"use client";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { useToast } from "@/hooks/use-toast";
import { z } from "zod";
import Link from "next/link";

export default function Page() {
  const form = useForm({
    resolver: zodResolver(
      z.object({ email: z.string(), password: z.string() }),
    ),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const { toast } = useToast();

  const onSubmit = form.handleSubmit((values) => {});

  return (
    <Card className="mx-auto w-full max-w-md space-y-6">
      <CardHeader className="pb-0">
        <CardTitle>Sign In</CardTitle>
        <CardDescription>
          Enter your email and password to sign in or use one of the options
          below.
        </CardDescription>
      </CardHeader>

      <CardContent className="space-y-4 py-0">
        <Form {...form}>
          <form onSubmit={onSubmit}>
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input placeholder="example@infinity.net" {...field} />
                  </FormControl>
                  <FormDescription>
                    Enter the email address associated with your account.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input type="password" placeholder="Password" {...field} />
                  </FormControl>
                  <FormDescription>
                    Your password must be at least 8 characters.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <div className="flex flex-col space-y-4 mt-4">
              <Button className="w-full">Sign In</Button>
            </div>
          </form>
        </Form>

        <span className="inline-block text-sm text-center w-full text-muted-foreground">
          Or sign in with social media
        </span>

        <div className="flex space-x-2">
          <Button variant="outline" className="w-full">
            Google
          </Button>
          <Button variant="outline" className="w-full">
            Facebook
          </Button>
          <Button variant="outline" className="w-full">
            Twitter
          </Button>
        </div>
      </CardContent>
      <CardFooter className="flex-col mt-0 gap-2">
        <div className="flex items-center justify-between w-full mt-4">
          <Link href="/forgot-password" className="text-sm hover:text-primary">
            Forgot Password?
          </Link>
          <span className="text-sm">
            Don't have an account?{" "}
            <Link href="/sign-up" className="hover:text-primary">
              Sign Up
            </Link>
          </span>
        </div>
      </CardFooter>
    </Card>
  );
}
