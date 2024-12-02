import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { aggregatedSideEffectSchema } from "../schemas/aggregated-side-effect.schema";

export const updateAggregatedSideEffectBodySchema = aggregatedSideEffectSchema
  .omit({
    totalReports: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateAggregatedSideEffectBodySchema = z.infer<
  typeof updateAggregatedSideEffectBodySchema
>;

export const updateAggregatedSideEffectResponseSchema =
  aggregatedSideEffectSchema;

export type UpdateAggregatedSideEffectResponseSchema = z.infer<
  typeof updateAggregatedSideEffectResponseSchema
>;

export async function updateAggregatedSideEffectApi(
  body: UpdateAggregatedSideEffectBodySchema,
): Promise<UpdateAggregatedSideEffectResponseSchema> {
  const response =
    await apiClient.put<UpdateAggregatedSideEffectResponseSchema>(
      `/aggregated-side-effects/${body.id}`,
      body,
    );
  return updateAggregatedSideEffectResponseSchema.parse(response.data);
}

export function useUpdateAggregatedSideEffectMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-aggregated-side-effect"] as const;

  return useMutation<
    UpdateAggregatedSideEffectResponseSchema,
    unknown,
    UpdateAggregatedSideEffectBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateAggregatedSideEffectApi(
        updateAggregatedSideEffectBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["aggregated-side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
