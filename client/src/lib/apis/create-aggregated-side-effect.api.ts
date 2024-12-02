import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { aggregatedSideEffectSchema } from "../schemas/aggregated-side-effect.schema";

export const createAggregatedSideEffectBodySchema =
  aggregatedSideEffectSchema.omit({
    id: true,
  });

export type CreateAggregatedSideEffectBodySchema = z.infer<
  typeof createAggregatedSideEffectBodySchema
>;

export const createAggregatedSideEffectResponseSchema =
  aggregatedSideEffectSchema;

export type CreateAggregatedSideEffectResponseSchema = z.infer<
  typeof createAggregatedSideEffectResponseSchema
>;

export async function createAggregatedSideEffectApi(
  body: CreateAggregatedSideEffectBodySchema,
): Promise<CreateAggregatedSideEffectResponseSchema> {
  const response =
    await apiClient.post<CreateAggregatedSideEffectResponseSchema>(
      `/aggregated-side-effects`,
      body,
    );
  return createAggregatedSideEffectResponseSchema.parse(response.data);
}

export function useCreateAggregatedSideEffectMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-aggregated-side-effect"] as const;

  return useMutation<
    CreateAggregatedSideEffectResponseSchema,
    unknown,
    CreateAggregatedSideEffectBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createAggregatedSideEffectApi(
        createAggregatedSideEffectBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["aggregated-side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
