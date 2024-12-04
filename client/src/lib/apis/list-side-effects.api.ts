import { z } from "zod";
import { sideEffectSchema } from "../schemas/side-effect.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listSideEffectsQuerySchema = pageableRequestSchema.extend({
  name: z.string().optional(),
  severity: z.string().optional(), // e.g., "Mild", "Moderate", "Severe"
});
export type ListSideEffectsQuerySchema = z.infer<
  typeof listSideEffectsQuerySchema
>;

export const listSideEffectsResponseSchema =
  createListResponseSchema(sideEffectSchema);
export type ListSideEffectsResponseSchema = z.infer<
  typeof listSideEffectsResponseSchema
>;

export const listSideEffectsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListSideEffectsErrorResponseSchema = z.infer<
  typeof listSideEffectsErrorResponseSchema
>;

export async function listSideEffectsApi(query: ListSideEffectsQuerySchema) {
  const response = await apiClient.get("/side-effects", query );
  return listSideEffectsResponseSchema.parse(response.data);
}

export function createListSideEffectsQueryOptions(
  query: ListSideEffectsQuerySchema,
) {
  const queryKey = ["side-effects", query] as const;
  return queryOptions<
    ListSideEffectsResponseSchema,
    ListSideEffectsErrorResponseSchema,
    ListSideEffectsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () => listSideEffectsApi(listSideEffectsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
