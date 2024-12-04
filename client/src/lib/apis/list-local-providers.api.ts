import { z } from "zod";
import { localProviderSchema } from "../schemas/local-provider.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listLocalProvidersQuerySchema = pageableRequestSchema.extend({
  email: z.string().optional(),
});
export type ListLocalProvidersQuerySchema = z.infer<
  typeof listLocalProvidersQuerySchema
>;

export const listLocalProvidersResponseSchema =
  createListResponseSchema(localProviderSchema);
export type ListLocalProvidersResponseSchema = z.infer<
  typeof listLocalProvidersResponseSchema
>;

export const listLocalProvidersErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListLocalProvidersErrorResponseSchema = z.infer<
  typeof listLocalProvidersErrorResponseSchema
>;

export async function listLocalProvidersApi(
  query: ListLocalProvidersQuerySchema,
) {
  const response = await apiClient.get("/local-providers",  query);
  return listLocalProvidersResponseSchema.parse(response.data);
}

export function createListLocalProvidersQueryOptions(
  query: ListLocalProvidersQuerySchema,
) {
  const queryKey = ["local-providers", query] as const;
  return queryOptions<
    ListLocalProvidersResponseSchema,
    ListLocalProvidersErrorResponseSchema,
    ListLocalProvidersQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listLocalProvidersApi(listLocalProvidersQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
