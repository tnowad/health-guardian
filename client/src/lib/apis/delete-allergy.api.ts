import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteAllergyParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteAllergyParamsSchema = z.infer<
  typeof deleteAllergyParamsSchema
>;

export const deleteAllergyResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteAllergyResponseSchema = z.infer<
  typeof deleteAllergyResponseSchema
>;

export async function deleteAllergyApi(
  params: DeleteAllergyParamsSchema,
): Promise<DeleteAllergyResponseSchema> {
  const response = await apiClient.delete<DeleteAllergyResponseSchema>(
    `/allergies/${params.id}`,
  );
  return deleteAllergyResponseSchema.parse(response.data);
}

export function useDeleteAllergyMutation(params: DeleteAllergyParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-allergy", params] as const;

  return useMutation<DeleteAllergyResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteAllergyApi(deleteAllergyParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["allergies"] });
    },
    throwOnError: isAxiosError,
  });
}