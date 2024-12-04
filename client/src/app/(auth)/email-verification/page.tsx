"use client";

import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useToast } from "@/hooks/use-toast";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { useVerifyEmailMutation } from "@/lib/apis/verify-email.api";
import { useSearchParams } from "next/navigation";
import { useResendVerificationMutation } from "@/lib/apis/resend-verification.api";
import Link from "next/link";

const verificationSchema = z.object({
  code: z
    .string()
    .length(6, { message: "Verification code must be 6 characters long" }),
});

export default function EmailVerification() {
  const searchParams = useSearchParams();
  const email = searchParams.get("email") || "";

  const { toast } = useToast();
  const [isVerified, setIsVerified] = useState(false);

  const form = useForm<z.infer<typeof verificationSchema>>({
    resolver: zodResolver(verificationSchema),
    defaultValues: {
      code: "",
    },
  });

  const verificationMutation = useVerifyEmailMutation();
  const resendVerificationMutation = useResendVerificationMutation();

  const onSubmit = async (values: z.infer<typeof verificationSchema>) =>
    verificationMutation.mutate(
      {
        email,
        code: values.code,
      },
      {
        onSuccess: (data) => {
          setIsVerified(true);
          toast({
            title: "Email Verified",
            description: data.message,
          });
        },
        onError: () => {
          toast({
            title: "Verification Error",
          });
        },
      },
    );

  const resendVerificationEmail = () => {
    resendVerificationMutation.mutate(
      {
        email,
      },
      {
        onSuccess: (data) => {
          toast({
            title: "Verification Email Sent",
            description: data.message,
          });
        },
        onError: () => {
          toast({
            title: "Resend Verification Error",
          });
        },
      },
    );
  };

  if (!email) {
    return (
      <Card className="mx-auto w-full max-w-md">
        <CardHeader>
          <CardTitle>Invalid Email</CardTitle>
          <CardDescription>
            The email address is invalid. Please try again.
          </CardDescription>
        </CardHeader>
      </Card>
    );
  }

  if (isVerified) {
    return (
      <Card className="mx-auto w-full max-w-md">
        <CardHeader>
          <CardTitle>Email Verified</CardTitle>
          <CardDescription>
            Your email has been successfully verified.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <p>Please sign in to continue.</p>
          <Button asChild className="w-full">
            <Link href="/sign-in">Sign In</Link>
          </Button>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card className="mx-auto w-full max-w-md">
      <CardHeader>
        <CardTitle>Verify Your Email</CardTitle>
        <CardDescription>
          Enter the verification code sent to {email}
        </CardDescription>
      </CardHeader>
      <CardContent>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <FormField
              control={form.control}
              name="code"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Verification Code</FormLabel>
                  <FormControl>
                    <Input placeholder="Enter 6-digit code" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" className="w-full">
              Verify Email
            </Button>
          </form>
        </Form>
      </CardContent>
      <CardFooter className="flex justify-center">
        <Button variant="link" onClick={resendVerificationEmail}>
          Resend Verification Email
        </Button>
      </CardFooter>
    </Card>
  );
}
