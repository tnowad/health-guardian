import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deletePatientLogParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeletePatientLogParamsSchema = z.infer<
  typeof deletePatientLogParamsSchema
>;

export const deletePatientLogResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeletePatientLogResponseSchema = z.infer<
  typeof deletePatientLogResponseSchema
>;

export async function deletePatientLogApi(
  params: DeletePatientLogParamsSchema,
): Promise<DeletePatientLogResponseSchema> {
  const response = await apiClient.delete<DeletePatientLogResponseSchema>(
    `/patient-logs/${params.id}`,
  );
  return deletePatientLogResponseSchema.parse(response.data);
}

export function useDeletePatientLogMutation(
  params: DeletePatientLogParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-patient-log", params] as const;

  return useMutation<DeletePatientLogResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deletePatientLogApi(deletePatientLogParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["patient-logs"] });
    },
    throwOnError: isAxiosError,
  });
}
