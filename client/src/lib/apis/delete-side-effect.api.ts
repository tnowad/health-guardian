import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteSideEffectParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteSideEffectParamsSchema = z.infer<
  typeof deleteSideEffectParamsSchema
>;

export const deleteSideEffectResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteSideEffectResponseSchema = z.infer<
  typeof deleteSideEffectResponseSchema
>;

export async function deleteSideEffectApi(
  params: DeleteSideEffectParamsSchema,
): Promise<DeleteSideEffectResponseSchema> {
  const response = await apiClient.delete<DeleteSideEffectResponseSchema>(
    `/side-effects/${params.id}`,
  );
  return deleteSideEffectResponseSchema.parse(response.data);
}

export function useDeleteSideEffectMutation(
  params: DeleteSideEffectParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-side-effect", params] as const;

  return useMutation<DeleteSideEffectResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteSideEffectApi(deleteSideEffectParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
