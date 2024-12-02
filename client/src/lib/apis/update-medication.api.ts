import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { medicationSchema } from "../schemas/medication.schema";

export const updateMedicationBodySchema = medicationSchema;

export type UpdateMedicationBodySchema = z.infer<
  typeof updateMedicationBodySchema
>;

export const updateMedicationResponseSchema = medicationSchema;

export type UpdateMedicationResponseSchema = z.infer<
  typeof updateMedicationResponseSchema
>;

export async function updateMedicationApi(
  body: UpdateMedicationBodySchema,
): Promise<UpdateMedicationResponseSchema> {
  const response = await apiClient.put<UpdateMedicationResponseSchema>(
    `/medications/${body.id}`,
    body,
  );
  return updateMedicationResponseSchema.parse(response.data);
}

export function useUpdateMedicationMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-medication"] as const;

  return useMutation<
    UpdateMedicationResponseSchema,
    unknown,
    UpdateMedicationBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateMedicationApi(updateMedicationBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["medications"] });
    },
    throwOnError: isAxiosError,
  });
}
