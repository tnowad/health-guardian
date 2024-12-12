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
  createDiagnosticReportBodySchema,
  CreateDiagnosticReportBodySchema,
  useCreateDiagnosticReportMutation,
} from "@/lib/apis/create-diagnostic-report.api";
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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { UploadFileInput } from "@/components/upload-file-input";

export function CreateDiagnosticReportForm() {
  const { toast } = useToast();
  const router = useRouter();
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const createDiagnosticReportMutation = useCreateDiagnosticReportMutation();
  const createDiagnosticReportForm = useForm<CreateDiagnosticReportBodySchema>({
    resolver: zodResolver(createDiagnosticReportBodySchema),
    defaultValues: {
      userId: currentUserInformationQuery.data?.userId,
      reportDate: new Date().toISOString(),
      reportType: "GENERAL_CHECKUP",
      summary: "",
      notes: "",
    },
  });

  const onSubmit = createDiagnosticReportForm.handleSubmit(async (values) =>
    createDiagnosticReportMutation.mutate(values, {
      onSuccess() {
        toast({
          title: "Diagnostic Report Created",
          description: "Your diagnostic report has been created successfully",
        });
        router.push("/reports");
      },
      onError() {
        toast({
          title: "Error creating diagnostic report",
          description:
            "An error occurred while creating your diagnostic report",
        });
      },
    }),
  );

  return (
    <Card className="w-full mx-auto">
      <CardHeader>
        <CardTitle>Create Diagnostic Report</CardTitle>
      </CardHeader>
      <Form {...createDiagnosticReportForm}>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <FormField
              control={createDiagnosticReportForm.control}
              name="reportDate"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Report Date</FormLabel>
                  <FormControl>
                    <Input type="date" {...field} />
                  </FormControl>
                  <FormDescription>
                    Enter the date of the report.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createDiagnosticReportForm.control}
              name="reportType"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Report Type</FormLabel>
                  <FormControl>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select report type" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="X_RAY">X-Ray</SelectItem>
                        <SelectItem value="CT_SCAN">CT Scan</SelectItem>
                        <SelectItem value="MRI">MRI</SelectItem>
                        <SelectItem value="BLOOD_TEST">Blood Test</SelectItem>
                        <SelectItem value="ECG">ECG</SelectItem>
                        <SelectItem value="GENERAL_CHECKUP">
                          General Checkup
                        </SelectItem>
                      </SelectContent>
                    </Select>
                  </FormControl>
                  <FormDescription>
                    Choose the type of diagnostic report.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />{" "}
            <FormField
              control={createDiagnosticReportForm.control}
              name="summary"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Summary</FormLabel>
                  <FormControl>
                    <Input placeholder="Summary of findings" {...field} />
                  </FormControl>
                  <FormDescription>
                    Optional field to summarize the findings.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={createDiagnosticReportForm.control}
              name="notes"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Notes</FormLabel>
                  <FormControl>
                    <Input placeholder="Additional notes" {...field} />
                  </FormControl>
                  <FormDescription>
                    Optional field for additional notes.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter>
            <Button type="submit" className="w-full">
              Create Diagnostic Report
            </Button>
          </CardFooter>
        </form>
      </Form>
    </Card>
  );
}
