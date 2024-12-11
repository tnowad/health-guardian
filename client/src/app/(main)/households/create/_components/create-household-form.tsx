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

type UploadImageInputProps = {
  onChange: ({ id, url }: { id: string; url: string }) => void;
};
function UploadImageInput({ onChange }: UploadImageInputProps) {}

export function CreateHouseholdForm() {
  const { toast } = useToast();
  const router = useRouter();
  const [avatarPreview, setAvatarPreview] = useState<string | null>(null);
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const createHouseholdMutation = useCreateHouseholdMutation();
  const createHouseholdForm = useForm<
    z.infer<typeof createHouseholdBodySchema>
  >({
    resolver: zodResolver(createHouseholdBodySchema),
    defaultValues: {
      name: "",
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

  const handleAvatarChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (!file) {
      return;
    }
  };
  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <CardTitle>Create Household</CardTitle>
      </CardHeader>
      <Form {...createHouseholdForm}>
        <form onSubmit={createHouseholdForm.handleSubmit(onSubmit)}>
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

            <div className="space-y-2">
              <Label htmlFor="avatar">Avatar</Label>
              <div className="flex items-center space-x-4">
                <Avatar className="w-16 h-16">
                  <AvatarImage
                    src={avatarPreview || "/placeholder.svg?height=64&width=64"}
                    alt="Avatar preview"
                  />
                  <AvatarFallback>AV</AvatarFallback>
                </Avatar>
                <Input
                  id="avatar"
                  type="file"
                  accept="image/*"
                  onChange={handleAvatarChange}
                  className="w-full"
                />
              </div>
            </div>
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
