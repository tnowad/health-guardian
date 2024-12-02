import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { aggregatedSideEffectSchema } from "../schemas/aggregated-side-effect.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";

export const getAggregatedSideEffectDetailResponseSchema =
  aggregatedSideEffectSchema;

export type GetAggregatedSideEffectDetailResponseSchema = z.infer<
  typeof getAggregatedSideEffectDetailResponseSchema
>;

export const getAggregatedSideEffectDetailErrorResponseSchema =
  z.discriminatedUnion("type", [unauthorizedResponseSchema]);

export type GetAggregatedSideEffectDetailErrorResponseSchema = z.infer<
  typeof getAggregatedSideEffectDetailErrorResponseSchema
>;

export async function getAggregatedSideEffectDetailApi(
  id: string,
): Promise<GetAggregatedSideEffectDetailResponseSchema> {
  const response =
    await apiClient.get<GetAggregatedSideEffectDetailResponseSchema>(
      `/aggregated-side-effects/${id}`,
    );
  return getAggregatedSideEffectDetailResponseSchema.parse(response.data);
}

export function useGetAggregatedSideEffectDetailQuery(id: string) {
  const queryKey = ["aggregated-side-effect-detail", id] as const;
  return queryOptions<
    GetAggregatedSideEffectDetailResponseSchema,
    GetAggregatedSideEffectDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getAggregatedSideEffectDetailApi(id),
  });
}
