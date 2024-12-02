import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteAggregatedSideEffectParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteAggregatedSideEffectParamsSchema = z.infer<
  typeof deleteAggregatedSideEffectParamsSchema
>;

export const deleteAggregatedSideEffectResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteAggregatedSideEffectResponseSchema = z.infer<
  typeof deleteAggregatedSideEffectResponseSchema
>;

export async function deleteAggregatedSideEffectApi(
  params: DeleteAggregatedSideEffectParamsSchema,
): Promise<DeleteAggregatedSideEffectResponseSchema> {
  const response =
    await apiClient.delete<DeleteAggregatedSideEffectResponseSchema>(
      `/aggregated-side-effects/${params.id}`,
    );
  return deleteAggregatedSideEffectResponseSchema.parse(response.data);
}

export function useDeleteAggregatedSideEffectMutation(
  params: DeleteAggregatedSideEffectParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-aggregated-side-effect", params] as const;

  return useMutation<DeleteAggregatedSideEffectResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteAggregatedSideEffectApi(
        deleteAggregatedSideEffectParamsSchema.parse(params),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["aggregated-side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
