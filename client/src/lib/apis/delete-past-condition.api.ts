import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deletePastConditionParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeletePastConditionParamsSchema = z.infer<
  typeof deletePastConditionParamsSchema
>;

export const deletePastConditionResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeletePastConditionResponseSchema = z.infer<
  typeof deletePastConditionResponseSchema
>;

export async function deletePastConditionApi(
  params: DeletePastConditionParamsSchema,
): Promise<DeletePastConditionResponseSchema> {
  const response = await apiClient.delete<DeletePastConditionResponseSchema>(
    `/past-conditions/${params.id}`,
  );
  return deletePastConditionResponseSchema.parse(response.data);
}

export function useDeletePastConditionMutation(params: DeletePastConditionParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-past-condition", params] as const;

  return useMutation<DeletePastConditionResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deletePastConditionApi(deletePastConditionParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["past-conditions"] });
    },
    throwOnError: isAxiosError,
  });
}

