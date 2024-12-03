import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { prescriptionSchema } from "../schemas/prescription.schema";

export const updatePrescriptionBodySchema = prescriptionSchema;

export type UpdatePrescriptionBodySchema = z.infer<
  typeof updatePrescriptionBodySchema
>;

export const updatePrescriptionResponseSchema = prescriptionSchema;

export type UpdatePrescriptionResponseSchema = z.infer<
  typeof updatePrescriptionResponseSchema
>;

export async function updatePrescriptionApi(
  body: UpdatePrescriptionBodySchema,
): Promise<UpdatePrescriptionResponseSchema> {
  const response = await apiClient.put<UpdatePrescriptionResponseSchema>(
    `/prescriptions/${body.id}`,
    body,
  );
  return updatePrescriptionResponseSchema.parse(response.data);
}

export function useUpdatePrescriptionMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-prescription"] as const;

  return useMutation<
    UpdatePrescriptionResponseSchema,
    unknown,
    UpdatePrescriptionBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updatePrescriptionApi(updatePrescriptionBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["prescriptions"] });
    },
    throwOnError: isAxiosError,
  });
}
