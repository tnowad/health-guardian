"use client";

import { useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { useToast } from "@/hooks/use-toast";
import {
  createHouseholdBodySchema,
  useCreateHouseholdMutation,
} from "@/lib/apis/create-household.api";
import { useRouter } from "next/navigation";
import { useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { useUploadFileMutation } from "@/lib/apis/upload-file.api";

type UploadFileProps = {
  onChange: ({ id, url }: { id: string; url: string }) => void;
};
function UploadFile({ onChange }: UploadFileProps) {
  const uploadFileMutation = useUploadFileMutation();
  const handleFileChange = async (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    const file = event.target.files?.[0];
    if (!file) {
      return;
    }

    const data = await uploadFileMutation.mutateAsync({ file });
    onChange(data);
  };
  return <Input type="file" onChange={handleFileChange} />;
}

export function CreateHouseholdForm() {
  const { toast } = useToast();
  const router = useRouter();
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const createHouseholdMutation = useCreateHouseholdMutation();
  const createHouseholdForm = useForm<
    z.infer<typeof createHouseholdBodySchema>
  >({
    resolver: zodResolver(createHouseholdBodySchema),
    defaultValues: {
      name: "My Household",
      avatar: "",
      headId: currentUserInformationQuery.data?.userId,
    },
  });

  const onSubmit = createHouseholdForm.handleSubmit(async (values) =>
    createHouseholdMutation.mutate(values, {
      onSuccess() {
        toast({
          title: "Household created",
          description: "Your household has been created successfully",
        });
        router.push("/households");
      },
      onError() {
        toast({
          title: "Error creating household",
          description: "An error occurred while creating your household",
        });
      },
    }),
  );

  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <CardTitle>Create Household</CardTitle>
      </CardHeader>
      <Form {...createHouseholdForm}>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <FormField
              control={createHouseholdForm.control}
              name="name"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Name</FormLabel>
                  <FormControl>
                    <Input placeholder="My Household" {...field} />
                  </FormControl>
                  <FormDescription>
                    Enter the name of your household.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={createHouseholdForm.control}
              name="avatar"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Avatar</FormLabel>
                  <FormControl>
                    <UploadFile
                      {...field}
                      onChange={(value) => {
                        field.onChange(value.id);
                      }}
                    />
                  </FormControl>
                  <FormDescription>
                    Upload an image to represent your household.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter>
            <Button type="submit" className="w-full">
              Create Household
            </Button>
          </CardFooter>
        </form>
      </Form>
    </Card>
  );
}
