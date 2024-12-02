import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteMedicationParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteMedicationParamsSchema = z.infer<
  typeof deleteMedicationParamsSchema
>;

export const deleteMedicationResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteMedicationResponseSchema = z.infer<
  typeof deleteMedicationResponseSchema
>;

export async function deleteMedicationApi(
  params: DeleteMedicationParamsSchema,
): Promise<DeleteMedicationResponseSchema> {
  const response = await apiClient.delete<DeleteMedicationResponseSchema>(
    `/medications/${params.id}`,
  );
  return deleteMedicationResponseSchema.parse(response.data);
}

export function useDeleteMedicationMutation(
  params: DeleteMedicationParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-medication", params] as const;

  return useMutation<DeleteMedicationResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteMedicationApi(deleteMedicationParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["medications"] });
    },
    throwOnError: isAxiosError,
  });
}
