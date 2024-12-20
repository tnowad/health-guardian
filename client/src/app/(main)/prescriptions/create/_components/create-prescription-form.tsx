"use client";

import { useState } from "react";
import { useForm, useFieldArray } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  CreatePrescriptionBodySchema,
  createPrescriptionBodySchema,
  useCreatePrescriptionMutation,
} from "@/lib/apis/create-prescription.api";
import { useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";

export function CreatePrescriptionForm() {
  const createPrescriptionMutation = useCreatePrescriptionMutation();
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const createPrescriptionForm = useForm<CreatePrescriptionBodySchema>({
    resolver: zodResolver(createPrescriptionBodySchema),
    defaultValues: {
      userId: currentUserInformationQuery.data?.userId,
      issuedBy: "",
      validUntil: "",
      issuedDate: new Date().toISOString().split("T")[0],
      status: "ACTIVE",
      items: [
        {
          medicationName: "",
          dosage: "",
          frequency: "",
          startDate: "",
          endDate: "",
          status: "ACTIVE",
        },
      ],
    },
  });

  const { fields, append, remove } = useFieldArray({
    control: createPrescriptionForm.control,
    name: "items",
  });

  const onSubmit = createPrescriptionForm.handleSubmit((values) =>
    createPrescriptionMutation.mutate(values, {
      onSuccess() {
        createPrescriptionForm.reset();
      },
      onError() {},
    }),
  );

  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <CardTitle>Create New Prescription</CardTitle>
      </CardHeader>
      <Form {...createPrescriptionForm}>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <FormField
              control={createPrescriptionForm.control}
              name="issuedBy"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Issued By</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createPrescriptionForm.control}
              name="validUntil"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Valid Until</FormLabel>
                  <FormControl>
                    <Input type="date" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createPrescriptionForm.control}
              name="issuedDate"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Issued Date</FormLabel>
                  <FormControl>
                    <Input type="date" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createPrescriptionForm.control}
              name="status"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Status</FormLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Select status" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      <SelectItem value="ACTIVE">Active</SelectItem>
                      <SelectItem value="COMPLETED">Completed</SelectItem>
                      <SelectItem value="EXPIRED">Expired</SelectItem>
                    </SelectContent>
                  </Select>
                  <FormMessage />
                </FormItem>
              )}
            />

            {fields.map((field, index) => (
              <div key={field.id} className="space-y-4 border p-4 rounded-md">
                <FormField
                  control={createPrescriptionForm.control}
                  name={`items.${index}.medicationName`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Medication Name</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={createPrescriptionForm.control}
                  name={`items.${index}.dosage`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Dosage</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={createPrescriptionForm.control}
                  name={`items.${index}.frequency`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Frequency</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={createPrescriptionForm.control}
                  name={`items.${index}.startDate`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Start Date</FormLabel>
                      <FormControl>
                        <Input type="date" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={createPrescriptionForm.control}
                  name={`items.${index}.endDate`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>End Date</FormLabel>
                      <FormControl>
                        <Input type="date" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={createPrescriptionForm.control}
                  name={`items.${index}.status`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Status</FormLabel>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                      >
                        <FormControl>
                          <SelectTrigger>
                            <SelectValue placeholder="Select status" />
                          </SelectTrigger>
                        </FormControl>
                        <SelectContent>
                          <SelectItem value="ACTIVE">Active</SelectItem>
                          <SelectItem value="COMPLETED">Completed</SelectItem>
                          <SelectItem value="EXPIRED">Expired</SelectItem>
                        </SelectContent>
                      </Select>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <Button
                  type="button"
                  variant="destructive"
                  onClick={() => remove(index)}
                >
                  Remove Medication
                </Button>
              </div>
            ))}

            <Button
              type="button"
              variant="outline"
              onClick={() =>
                append({
                  medicationName: "",
                  dosage: "",
                  frequency: "",
                  startDate: "",
                  endDate: "",
                  status: "ACTIVE",
                })
              }
            >
              Add Medication
            </Button>
          </CardContent>
          <CardFooter>
            <Button
              type="submit"
              disabled={createPrescriptionMutation.isPending}
            >
              {createPrescriptionMutation.isPending
                ? "Creating..."
                : "Create Prescription"}
            </Button>
          </CardFooter>
        </form>
      </Form>
    </Card>
  );
}
