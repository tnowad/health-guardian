import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteImmunizationParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteImmunizationParamsSchema = z.infer<
  typeof deleteImmunizationParamsSchema
>;

export const deleteImmunizationResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteImmunizationResponseSchema = z.infer<
  typeof deleteImmunizationResponseSchema
>;

export async function deleteImmunizationApi(
  params: DeleteImmunizationParamsSchema,
): Promise<DeleteImmunizationResponseSchema> {
  const response = await apiClient.delete<DeleteImmunizationResponseSchema>(
    `/immunizations/${params.id}`,
  );
  return deleteImmunizationResponseSchema.parse(response.data);
}

export function useDeleteImmunizationMutation(params: DeleteImmunizationParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-immunization", params] as const;

  return useMutation<DeleteImmunizationResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteImmunizationApi(deleteImmunizationParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["immunizations"] });
    },
    throwOnError: isAxiosError,
  });
}