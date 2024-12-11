import { z } from "zod";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "@/lib/schemas/page.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";
import { familyHistorySchema } from "@/lib/schemas/family-history/family-history.schema";

export const listFamilyHistoriesQuerySchema = pageableRequestSchema.extend({
  userId: z.string().uuid().optional(),
  relation: z.string().optional(),
  condition: z.string().optional(),
  description: z.string().optional(),
});
export type ListFamilyHistoriesQuerySchema = z.infer<
  typeof listFamilyHistoriesQuerySchema
>;

export const listFamilyHistoriesResponseSchema =
  createListResponseSchema(familyHistorySchema);
export type ListFamilyHistoriesResponseSchema = z.infer<
  typeof listFamilyHistoriesResponseSchema
>;

export const listFamilyHistoriesErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type ListFamilyHistoriesErrorResponseSchema = z.infer<
  typeof listFamilyHistoriesErrorResponseSchema
>;

export async function listFamilyHistoriesApi(
  query: ListFamilyHistoriesQuerySchema
) {
  const response = await apiClient.get<ListFamilyHistoriesResponseSchema>(
    "/family-histories",
    query
  );
  return response.data;
}

export function createListFamilyHistoriesQueryOptions(
  query: ListFamilyHistoriesQuerySchema
) {
  const queryKey = ["family-histories", query] as const;
  return queryOptions<ListFamilyHistoriesResponseSchema>({
    queryKey,
    queryFn: () =>
      listFamilyHistoriesApi(listFamilyHistoriesQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
