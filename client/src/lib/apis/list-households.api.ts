import { z } from "zod";
import { householdSchema } from "../schemas/household.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listHouseholdsQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  headId: z.string().uuid().optional(),
});
export type ListHouseholdsQuerySchema = z.infer<
  typeof listHouseholdsQuerySchema
>;

export const listHouseholdsResponseSchema =
  createListResponseSchema(householdSchema);
export type ListHouseholdsResponseSchema = z.infer<
  typeof listHouseholdsResponseSchema
>;

export const listHouseholdsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListHouseholdsErrorResponseSchema = z.infer<
  typeof listHouseholdsErrorResponseSchema
>;

export async function listHouseholdsApi(query: ListHouseholdsQuerySchema) {
  const response = await apiClient.get("/households", query);
  return listHouseholdsResponseSchema.parse(response.data);
}

export function createListHouseholdsQueryOptions(
  query: ListHouseholdsQuerySchema,
) {
  const queryKey = ["households", query] as const;
  return queryOptions<ListHouseholdsResponseSchema>({
    queryKey,
    queryFn: () => listHouseholdsApi(listHouseholdsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
