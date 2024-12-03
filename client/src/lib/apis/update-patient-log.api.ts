import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { patientLogSchema } from "../schemas/patient-log.schema";

export const updatePatientLogBodySchema = patientLogSchema;

export type UpdatePatientLogBodySchema = z.infer<
  typeof updatePatientLogBodySchema
>;

export const updatePatientLogResponseSchema = patientLogSchema;

export type UpdatePatientLogResponseSchema = z.infer<
  typeof updatePatientLogResponseSchema
>;

export async function updatePatientLogApi(
  body: UpdatePatientLogBodySchema,
): Promise<UpdatePatientLogResponseSchema> {
  const response = await apiClient.put<UpdatePatientLogResponseSchema>(
    `/patient-logs/${body.id}`,
    body,
  );
  return updatePatientLogResponseSchema.parse(response.data);
}

export function useUpdatePatientLogMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-patient-log"] as const;

  return useMutation<
    UpdatePatientLogResponseSchema,
    unknown,
    UpdatePatientLogBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updatePatientLogApi(updatePatientLogBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["patient-logs"] });
    },
    throwOnError: isAxiosError,
  });
}

