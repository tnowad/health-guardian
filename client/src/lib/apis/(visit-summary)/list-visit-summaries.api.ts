import { z } from "zod";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "@/lib/schemas/page.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { visitSummarySchema } from "@/lib/schemas/(visit-summary)/visit-summary.schema";

export const listVisitSummariesQuerySchema = pageableRequestSchema.extend({
  userId: z.string().uuid().optional(),
  visitDate: z.string().optional(),
  visitType: z.string().optional(),
  summary: z.string().optional(),
  notes: z.string().optional(),
});
export type ListVisitSummariesQuerySchema = z.infer<
  typeof listVisitSummariesQuerySchema
>;

export const listVisitSummariesResponseSchema =
  createListResponseSchema(visitSummarySchema);
export type ListVisitSummariesResponseSchema = z.infer<
  typeof listVisitSummariesResponseSchema
>;

export const listVisitSummariesErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type ListVisitSummariesErrorResponseSchema = z.infer<
  typeof listVisitSummariesErrorResponseSchema
>;

export async function listVisitSummariesApi(
  query: ListVisitSummariesQuerySchema
) {
  const response = await apiClient.get<ListVisitSummariesResponseSchema>(
    "/visit-summaries",
    query
  );
  return response.data;
}

export function createListVisitSummariesQueryOptions(
  query: ListVisitSummariesQuerySchema
) {
  const queryKey = ["visitSummaries", query] as const;
  return queryOptions<ListVisitSummariesResponseSchema>({
    queryKey,
    queryFn: () =>
      listVisitSummariesApi(listVisitSummariesQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
