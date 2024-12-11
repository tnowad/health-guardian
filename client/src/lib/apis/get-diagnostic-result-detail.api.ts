import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { diagnosticResultSchema } from "../schemas/diagnostic-result.schema"; // Import diagnosticResultSchema

// Response schema for getting the details of a diagnostic result
export const getDiagnosticResultDetailResponseSchema = diagnosticResultSchema;

export type GetDiagnosticResultDetailResponseSchema = z.infer<
  typeof getDiagnosticResultDetailResponseSchema
>;

// Error response schema for unauthorized access
export const getDiagnosticResultDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetDiagnosticResultDetailErrorResponseSchema = z.infer<
  typeof getDiagnosticResultDetailErrorResponseSchema
>;

// API call to get details of a diagnostic result
export async function getDiagnosticResultDetailApi(
  id: string
): Promise<GetDiagnosticResultDetailResponseSchema> {
  const response = await apiClient.get<GetDiagnosticResultDetailResponseSchema>(
    `/diagnostic-results/${id}`
  );
  return getDiagnosticResultDetailResponseSchema.parse(response.data);
}

// Query hook for fetching the details of a diagnostic result
export function useGetDiagnosticResultDetailQuery(id: string) {
  const queryKey = ["diagnostic-result-detail", id] as const;

  return queryOptions<
    GetDiagnosticResultDetailResponseSchema,
    GetDiagnosticResultDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id, // Only enable the query if the ID is provided
    queryFn: () => getDiagnosticResultDetailApi(id),
  });
}
