import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { patientSchema } from "../schemas/patient.schema";

export const updatePatientBodySchema = patientSchema;

export type UpdatePatientBodySchema = z.infer<typeof updatePatientBodySchema>;

export const updatePatientResponseSchema = patientSchema;

export type UpdatePatientResponseSchema = z.infer<
  typeof updatePatientResponseSchema
>;

export async function updatePatientApi(
  body: UpdatePatientBodySchema,
): Promise<UpdatePatientResponseSchema> {
  const response = await apiClient.put<UpdatePatientResponseSchema>(
    `/patients/${body.id}`,
    body,
  );
  return updatePatientResponseSchema.parse(response.data);
}

export function useUpdatePatientMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-patient"] as const;

  return useMutation<
    UpdatePatientResponseSchema,
    unknown,
    UpdatePatientBodySchema
  >({
    mutationKey,
    mutationFn: (body) => updatePatientApi(updatePatientBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["patients"] });
    },
    throwOnError: isAxiosError,
  });
}
