import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { diagnosticReportSchema } from "../schemas/diagnostic-report.schema"; // Import diagnosticReportSchema

// Response schema for getting the details of a diagnostic report
export const getDiagnosticReportDetailResponseSchema = diagnosticReportSchema;

export type GetDiagnosticReportDetailResponseSchema = z.infer<
  typeof getDiagnosticReportDetailResponseSchema
>;

// Error response schema for unauthorized access
export const getDiagnosticReportDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetDiagnosticReportDetailErrorResponseSchema = z.infer<
  typeof getDiagnosticReportDetailErrorResponseSchema
>;

// API call to get details of a diagnostic report
export async function getDiagnosticReportDetailApi(
  id: string
): Promise<GetDiagnosticReportDetailResponseSchema> {
  const response = await apiClient.get<GetDiagnosticReportDetailResponseSchema>(
    `/diagnostic-reports/${id}`
  );
  return getDiagnosticReportDetailResponseSchema.parse(response.data);
}

// Query hook for fetching the details of a diagnostic report
export function useGetDiagnosticReportDetailQuery(id: string) {
  const queryKey = ["diagnostic-report-detail", id] as const;

  return queryOptions<
    GetDiagnosticReportDetailResponseSchema,
    GetDiagnosticReportDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id, // Only enable the query if the ID is provided
    queryFn: () => getDiagnosticReportDetailApi(id),
  });
}
