import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { diagnosticReportSchema } from "../schemas/diagnostic-report.schema"; // Import the diagnostic report schema

// Omit 'id' for the create body schema
export const createDiagnosticReportBodySchema = diagnosticReportSchema.omit({
  id: true,
});

export type CreateDiagnosticReportBodySchema = z.infer<
  typeof createDiagnosticReportBodySchema
>;

// The response schema is the full diagnostic report schema
export const createDiagnosticReportResponseSchema = diagnosticReportSchema;

export type CreateDiagnosticReportResponseSchema = z.infer<
  typeof createDiagnosticReportResponseSchema
>;

// API call to create a diagnostic report
export async function createDiagnosticReportApi(
  body: CreateDiagnosticReportBodySchema
): Promise<CreateDiagnosticReportResponseSchema> {
  const response = await apiClient.post<CreateDiagnosticReportResponseSchema>(
    `/diagnostic-reports`,
    body
  );
  return createDiagnosticReportResponseSchema.parse(response.data);
}

// Mutation hook for creating a diagnostic report
export function useCreateDiagnosticReportMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-diagnostic-report"] as const;

  return useMutation<
    CreateDiagnosticReportResponseSchema,
    unknown,
    CreateDiagnosticReportBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createDiagnosticReportApi(createDiagnosticReportBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["diagnostic-reports"] });
    },
    throwOnError: isAxiosError,
  });
}
