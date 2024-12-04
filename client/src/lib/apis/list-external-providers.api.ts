import { z } from "zod";
import { externalProviderSchema } from "../schemas/external-provider.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listExternalProvidersQuerySchema = pageableRequestSchema.extend({
  providerName: z.string().optional(),
});
export type ListExternalProvidersQuerySchema = z.infer<
  typeof listExternalProvidersQuerySchema
>;

export const listExternalProvidersResponseSchema = createListResponseSchema(
  externalProviderSchema,
);
export type ListExternalProvidersResponseSchema = z.infer<
  typeof listExternalProvidersResponseSchema
>;

export const listExternalProvidersErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListExternalProvidersErrorResponseSchema = z.infer<
  typeof listExternalProvidersErrorResponseSchema
>;

export async function listExternalProvidersApi(
  query: ListExternalProvidersQuerySchema,
) {
  const response = await apiClient.get("/external-providers",query);
  return listExternalProvidersResponseSchema.parse(response.data);
}

export function createListExternalProvidersQueryOptions(
  query: ListExternalProvidersQuerySchema,
) {
  const queryKey = ["external-providers", query] as const;
  return queryOptions<
    ListExternalProvidersResponseSchema,
    ListExternalProvidersErrorResponseSchema,
    ListExternalProvidersQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listExternalProvidersApi(listExternalProvidersQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
