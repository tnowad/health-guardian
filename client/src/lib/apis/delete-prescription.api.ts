import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deletePrescriptionParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeletePrescriptionParamsSchema = z.infer<
  typeof deletePrescriptionParamsSchema
>;

export const deletePrescriptionResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeletePrescriptionResponseSchema = z.infer<
  typeof deletePrescriptionResponseSchema
>;

export async function deletePrescriptionApi(
  params: DeletePrescriptionParamsSchema,
): Promise<DeletePrescriptionResponseSchema> {
  const response = await apiClient.delete<DeletePrescriptionResponseSchema>(
    `/prescriptions/${params.id}`,
  );
  return deletePrescriptionResponseSchema.parse(response.data);
}

export function useDeletePrescriptionMutation(
  params: DeletePrescriptionParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-prescription", params] as const;

  return useMutation<DeletePrescriptionResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deletePrescriptionApi(deletePrescriptionParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["prescriptions"] });
    },
    throwOnError: isAxiosError,
  });
}

