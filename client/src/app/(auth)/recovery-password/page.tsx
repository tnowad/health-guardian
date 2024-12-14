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

export default function PasswordRecoveryPage() {
  const passwordRecoveryForm = useForm({
    defaultValues: {
      password: "",
      confirmPassword: "",
    },
  });

  const onSubmit = passwordRecoveryForm.handleSubmit((values) => {
    console.log("Password Recovery Submitted", values);
  });

  return (
    <Card className="mx-auto w-full max-w-md space-y-6">
      <CardHeader className="pb-0">
        <CardTitle>Recover Password</CardTitle>
        <CardDescription>
          Enter your new password below to reset it.
        </CardDescription>
      </CardHeader>

      <CardContent className="py-0">
        <Form {...passwordRecoveryForm}>
          <form onSubmit={onSubmit} className="space-y-4">
            <FormField
              control={passwordRecoveryForm.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>New Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="New Password"
                      {...field}
                    />
                  </FormControl>
                  <FormDescription>
                    Your password must be at least 8 characters.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={passwordRecoveryForm.control}
              name="confirmPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Confirm Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Confirm Password"
                      {...field}
                    />
                  </FormControl>
                  <FormDescription>
                    Re-enter your new password to confirm.
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
