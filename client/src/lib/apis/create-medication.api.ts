import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { medicationSchema } from "../schemas/medication.schema";

export const createMedicationBodySchema = medicationSchema.omit({ id: true });

export type CreateMedicationBodySchema = z.infer<
  typeof createMedicationBodySchema
>;

export const createMedicationResponseSchema = medicationSchema;

export type CreateMedicationResponseSchema = z.infer<
  typeof createMedicationResponseSchema
>;

export async function createMedicationApi(
  body: CreateMedicationBodySchema,
): Promise<CreateMedicationResponseSchema> {
  const response = await apiClient.post<CreateMedicationResponseSchema>(
    `/medications`,
    body,
  );
  return createMedicationResponseSchema.parse(response.data);
}

export function useCreateMedicationMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-medication"] as const;

  return useMutation<
    CreateMedicationResponseSchema,
    unknown,
    CreateMedicationBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createMedicationApi(createMedicationBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["medications"] });
    },
    throwOnError: isAxiosError,
  });
}
