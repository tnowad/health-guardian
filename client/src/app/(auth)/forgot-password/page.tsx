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
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import Link from "next/link";

export default function ForgotPasswordPage() {
  const forgotPasswordForm = useForm({
    defaultValues: {
      email: "",
    },
  });

  const onSubmit = forgotPasswordForm.handleSubmit((values) => {
    console.log("Forgot Password Submitted", values);
  });

  return (
    <Card className="mx-auto w-full max-w-md space-y-6">
      <CardHeader className="pb-0">
        <CardTitle>Forgot Password</CardTitle>
        <CardDescription>
          Enter your email address to reset your password.
        </CardDescription>
      </CardHeader>

      <CardContent className="py-0">
        <Form {...forgotPasswordForm}>
          <form onSubmit={onSubmit} className="space-y-4">
            <FormField
              control={forgotPasswordForm.control}
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

            <div className="flex flex-col space-y-4 mt-4">
              <Button className="w-full">Reset Password</Button>
            </div>
          </form>
        </Form>
      </CardContent>
      <CardFooter className="flex-col mt-0 gap-2">
        <div className="flex items-center justify-center w-full mt-4">
          <Link href="/sign-in" className="text-sm hover:text-primary">
            Back to Sign In
          </Link>
        </div>
      </CardFooter>
    </Card>
  );
}
