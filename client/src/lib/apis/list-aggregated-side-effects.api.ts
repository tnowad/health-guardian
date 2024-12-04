import { z } from "zod";
import { aggregatedSideEffectSchema } from "../schemas/aggregated-side-effect.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listAggregatedSideEffectsQuerySchema =
  pageableRequestSchema.extend({
    periodStart: z.string().optional(),
    periodEnd: z.string().optional(),
    medicationId: z.string().uuid().optional(),
    sideEffectId: z.string().uuid().optional(),
  });
export type ListAggregatedSideEffectsQuerySchema = z.infer<
  typeof listAggregatedSideEffectsQuerySchema
>;

export const listAggregatedSideEffectsResponseSchema = createListResponseSchema(
  aggregatedSideEffectSchema,
);
export type ListAggregatedSideEffectsResponseSchema = z.infer<
  typeof listAggregatedSideEffectsResponseSchema
>;

export const listAggregatedSideEffectsErrorResponseSchema =
  z.discriminatedUnion("type", [unauthorizedResponseSchema]);
export type ListAggregatedSideEffectsErrorResponseSchema = z.infer<
  typeof listAggregatedSideEffectsErrorResponseSchema
>;

export async function listAggregatedSideEffectsApi(
  query: ListAggregatedSideEffectsQuerySchema,
) {
  const response = await apiClient.get("/aggregated-side-effects", query);
  return listAggregatedSideEffectsResponseSchema.parse(response.data);
}

export function createListAggregatedSideEffectsQueryOptions(
  query: ListAggregatedSideEffectsQuerySchema,
) {
  const queryKey = ["aggregated-side-effects", query] as const;
  return queryOptions<
    ListAggregatedSideEffectsResponseSchema,
    ListAggregatedSideEffectsErrorResponseSchema,
    ListAggregatedSideEffectsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listAggregatedSideEffectsApi(
        listAggregatedSideEffectsQuerySchema.parse(query),
      ),
    throwOnError: isAxiosError,
  });
}
