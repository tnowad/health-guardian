import { z } from "zod";
import { diagnosticResultSchema } from "../schemas/diagnostic-result.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listDiagnosticResultsQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  userId: z.string().uuid(),
});

export type ListDiagnosticResultsQuerySchema = z.infer<
  typeof listDiagnosticResultsQuerySchema
>;

export const listDiagnosticResultsResponseSchema = createListResponseSchema(
  diagnosticResultSchema,
);

export type ListDiagnosticResultsResponseSchema = z.infer<
  typeof listDiagnosticResultsResponseSchema
>;

export const listDiagnosticResultsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type ListDiagnosticResultsErrorResponseSchema = z.infer<
  typeof listDiagnosticResultsErrorResponseSchema
>;


export async function listDiagnosticResultsApi(query: ListDiagnosticResultsQuerySchema) {
  const response = await apiClient.get<ListDiagnosticResultsResponseSchema>(
    "/diagnostic-results",
    query,
  );
  return response.data;
}

export function createListDiagnosticResultsQueryOptions(
  query: ListDiagnosticResultsQuerySchema,
) {
  const queryKey = ["diagnostic-results", query] as const;
  return queryOptions<ListDiagnosticResultsResponseSchema>({
    queryKey,
    queryFn: () => listDiagnosticResultsApi(listDiagnosticResultsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}