import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { patientSchema } from "../schemas/patient.schema";

export const createPatientBodySchema = patientSchema.omit({
  id: true,
  createdAt: true,
  updatedAt: true,
});

export type CreatePatientBodySchema = z.infer<typeof createPatientBodySchema>;

export const createPatientResponseSchema = patientSchema;

export type CreatePatientResponseSchema = z.infer<
  typeof createPatientResponseSchema
>;

export async function createPatientApi(
  body: CreatePatientBodySchema,
): Promise<CreatePatientResponseSchema> {
  const response = await apiClient.post<CreatePatientResponseSchema>(
    `/patients`,
    body,
  );
  return createPatientResponseSchema.parse(response.data);
}

export function useCreatePatientMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-patient"] as const;

  return useMutation<
    CreatePatientResponseSchema,
    unknown,
    CreatePatientBodySchema
  >({
    mutationKey,
    mutationFn: (body) => createPatientApi(createPatientBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["patients"] });
    },
    throwOnError: isAxiosError,
  });
}
