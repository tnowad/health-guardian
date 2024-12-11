import { z } from "zod";
import { diagnosticReportSchema } from "../schemas/diagnostic-report.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listDiagnosticReportsQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  userId: z.string().uuid(),
});

export type ListDiagnosticReportsQuerySchema = z.infer<
  typeof listDiagnosticReportsQuerySchema
>;

export const listDiagnosticReportsResponseSchema = createListResponseSchema(
  diagnosticReportSchema,
);

export type ListDiagnosticReportsResponseSchema = z.infer<
  typeof listDiagnosticReportsResponseSchema
>;

export const listDiagnosticReportsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type ListDiagnosticReportsErrorResponseSchema = z.infer<
  typeof listDiagnosticReportsErrorResponseSchema
>;

export async function listDiagnosticReportsApi(query: ListDiagnosticReportsQuerySchema) {
  const response = await apiClient.get<ListDiagnosticReportsResponseSchema>(
    "/diagnostic-reports",
    query,
  );
  return response.data;
}

export function createListDiagnosticReportsQueryOptions(
  query: ListDiagnosticReportsQuerySchema,
) {
  const queryKey = ["diagnostic-reports", query] as const;
  return queryOptions<ListDiagnosticReportsResponseSchema>({
    queryKey,
    queryFn: () => listDiagnosticReportsApi(listDiagnosticReportsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
