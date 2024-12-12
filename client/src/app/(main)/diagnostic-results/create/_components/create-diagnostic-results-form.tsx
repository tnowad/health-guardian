"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
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
  useCreateDiagnosticResultMutation,
  createDiagnosticResultBodySchema,
} from "@/lib/apis/create-diagnostic-result.api";
import { useRouter } from "next/navigation";
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
import { diagnosticResultSchema } from "@/lib/schemas/diagnostic-result.schema";
import { z } from "zod";

export type DiagnosticResultSchema = z.infer<typeof diagnosticResultSchema>;

export function CreateDiagnosticResultsForm() {
  const { toast } = useToast();
  const router = useRouter();

  const createDiagnosticResultMutation = useCreateDiagnosticResultMutation();
  const createDiagnosticResultsForm = useForm<DiagnosticResultSchema>({
    resolver: zodResolver(diagnosticResultSchema),
    defaultValues: {
      id: "",
      userId: "", // This should be populated based on the user context
      testName: "",
      resultDate: new Date().toISOString(),
      resultValue: "",
      notes: "",
    },
  });

  const onSubmit = createDiagnosticResultsForm.handleSubmit(async (values) =>
    createDiagnosticResultMutation.mutate(values, {
      onSuccess() {
        toast({
          title: "Diagnostic Result Created",
          description: "Your diagnostic result has been created successfully",
        });
        router.push("/results");
      },
      onError() {
        toast({
          title: "Error creating diagnostic result",
          description:
            "An error occurred while creating your diagnostic result",
        });
      },
    }),
  );

  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <CardTitle>Create Diagnostic Result</CardTitle>
      </CardHeader>
      <Form {...createDiagnosticResultsForm}>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <FormField
              control={createDiagnosticResultsForm.control}
              name="testName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Test Name</FormLabel>
                  <FormControl>
                    <Input placeholder="Name of the test" {...field} />
                  </FormControl>
                  <FormDescription>
                    Enter the name of the diagnostic test.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createDiagnosticResultsForm.control}
              name="resultDate"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Result Date</FormLabel>
                  <FormControl>
                    <Input type="datetime-local" {...field} />
                  </FormControl>
                  <FormDescription>
                    Enter the date of the result.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createDiagnosticResultsForm.control}
              name="resultValue"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Result Value</FormLabel>
                  <FormControl>
                    <Input placeholder="Result value" {...field} />
                  </FormControl>
                  <FormDescription>
                    Enter the value of the diagnostic test result.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createDiagnosticResultsForm.control}
              name="notes"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Notes</FormLabel>
                  <FormControl>
                    <Input placeholder="Additional notes" {...field} />
                  </FormControl>
                  <FormDescription>
                    Optional field for additional notes about the result.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createDiagnosticResultsForm.control}
              name="file"
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
                    Upload an image to represent your household.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter>
            <Button type="submit" className="w-full">
              Create Diagnostic Result
            </Button>
          </CardFooter>
        </form>
      </Form>
    </Card>
  );
}
