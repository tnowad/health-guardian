import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import {prescriptionSchema} from "../schemas/prescription.schema";

export const createPrescriptionBodySchema = prescriptionSchema.omit({ id: true });

export type CreatePrescriptionBodySchema = z.infer<
  typeof createPrescriptionBodySchema
>;

export const createPrescriptionResponseSchema = prescriptionSchema;

export type CreatePrescriptionResponseSchema = z.infer<
  typeof createPrescriptionResponseSchema
>;

export async function createPrescriptionApi(
  body: CreatePrescriptionBodySchema,
): Promise<CreatePrescriptionResponseSchema> {
  const response = await apiClient.post<CreatePrescriptionResponseSchema>(
    `/prescriptions`,
    body,
  );
  return createPrescriptionResponseSchema.parse(response.data);
}

export function useCreatePrescriptionMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-prescription"] as const;

  return useMutation<
      CreatePrescriptionResponseSchema,
    unknown,
    CreatePrescriptionBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createPrescriptionApi(createPrescriptionBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["prescriptions"] });
    },
    throwOnError: isAxiosError,
  });
}

