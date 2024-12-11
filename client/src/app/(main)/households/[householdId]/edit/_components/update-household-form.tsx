"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import {
  updateHouseholdBodySchema,
  useUpdateHouseholdMutation,
} from "@/lib/apis/update-household.api";
import { useRouter } from "next/navigation";
import { useSuspenseQuery } from "@tanstack/react-query";
import { createGetHouseholdDetailQueryOptions } from "@/lib/apis/get-household-detail.api";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { UploadFileInput } from "@/components/upload-file-input";

export function UpdateHouseholdForm({ householdId }: { householdId: string }) {
  const { toast } = useToast();
  const router = useRouter();
  const householdDetailsQuery = useSuspenseQuery(
    createGetHouseholdDetailQueryOptions(householdId),
  );

  const updateHouseholdMutation = useUpdateHouseholdMutation();
  const updateHouseholdForm = useForm<
    z.infer<typeof updateHouseholdBodySchema>
  >({
    resolver: zodResolver(updateHouseholdBodySchema),
    defaultValues: {
      name: householdDetailsQuery.data?.name || "",
      avatar: householdDetailsQuery.data?.avatar || "",
    },
  });

  const onSubmit = updateHouseholdForm.handleSubmit(async (values) =>
    updateHouseholdMutation.mutate(
      { ...values },
      {
        onSuccess() {
          toast({
            title: "Household updated",
            description: "Your household has been updated successfully",
          });
          router.push(`/households/${householdId}`);
        },
        onError() {
          toast({
            title: "Error updating household",
            description: "An error occurred while updating your household",
          });
        },
      },
    ),
  );

  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <CardTitle>Update Household</CardTitle>
      </CardHeader>
      <Form {...updateHouseholdForm}>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <FormField
              control={updateHouseholdForm.control}
              name="name"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Name</FormLabel>
                  <FormControl>
                    <Input placeholder="My Household" {...field} />
                  </FormControl>
                  <FormDescription>
                    Update the name of your household.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={updateHouseholdForm.control}
              name="avatar"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Avatar</FormLabel>
                  <FormControl>
                    <UploadFileInput
                      {...field}
                      onChange={(value) => {
                        field.onChange(value.id);
                      }}
                    />
                  </FormControl>
                  <FormDescription>
                    Update the image representing your household.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter>
            <Button type="submit" className="w-full">
              Update Household
            </Button>
          </CardFooter>
        </form>
      </Form>
    </Card>
  );
}
