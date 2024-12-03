import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { patientLogSchema } from "../schemas/patient-log.schema";

export const createPatientLogBodySchema = patientLogSchema.omit({ id: true });

export type CreatePatientLogBodySchema = z.infer<
  typeof createPatientLogBodySchema
>;

export const createPatientLogResponseSchema = patientLogSchema;

export type CreatePatientLogResponseSchema = z.infer<
  typeof createPatientLogResponseSchema
>;

export async function createPatientLogApi(
  body: CreatePatientLogBodySchema,
): Promise<CreatePatientLogResponseSchema> {
  const response = await apiClient.post<CreatePatientLogResponseSchema>(
    `/patient-logs`,
    body,
  );
  return createPatientLogResponseSchema.parse(response.data);
}

export function useCreatePatientLogMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-patient-log"] as const;

  return useMutation<
      CreatePatientLogResponseSchema,
    unknown,
    CreatePatientLogBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createPatientLogApi(createPatientLogBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["patient-logs"] });
    },
    throwOnError: isAxiosError,
  });
}
