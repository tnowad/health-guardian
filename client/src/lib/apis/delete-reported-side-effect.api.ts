import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteReportedSideEffectParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteReportedSideEffectParamsSchema = z.infer<
  typeof deleteReportedSideEffectParamsSchema
>;

export const deleteReportedSideEffectResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteReportedSideEffectResponseSchema = z.infer<
  typeof deleteReportedSideEffectResponseSchema
>;

export async function deleteReportedSideEffectApi(
  params: DeleteReportedSideEffectParamsSchema,
): Promise<DeleteReportedSideEffectResponseSchema> {
  const response =
    await apiClient.delete<DeleteReportedSideEffectResponseSchema>(
      `/reported-side-effects/${params.id}`,
    );
  return deleteReportedSideEffectResponseSchema.parse(response.data);
}

export function useDeleteReportedSideEffectMutation(
  params: DeleteReportedSideEffectParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-reported-side-effect", params] as const;

  return useMutation<DeleteReportedSideEffectResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteReportedSideEffectApi(
        deleteReportedSideEffectParamsSchema.parse(params),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["reported-side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
