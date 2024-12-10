import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

const signOutSchema = z.object({
  confirm: z.boolean().refine((val) => val, {
    message: "You must confirm to sign out",
  }),
});

type SignOutFormValues = z.infer<typeof signOutSchema>;

export default function SignOutForm() {
  const form = useForm<SignOutFormValues>({
    resolver: zodResolver(signOutSchema),
    defaultValues: {
      confirm: false,
    },
  });

  const onSubmit = async (data: SignOutFormValues) => {
    console.log("Signing out", data);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        <FormField
          name="confirm"
          control={form.control}
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <label className="flex items-center space-x-2">
                  <span>Confirm Sign Out</span>
                </label>
              </FormControl>
            </FormItem>
          )}
        />
        <Button type="submit" variant="destructive">
          Sign Out
        </Button>
      </form>
    </Form>
  );
}
