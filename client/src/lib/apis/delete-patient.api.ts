import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deletePatientParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeletePatientParamsSchema = z.infer<
  typeof deletePatientParamsSchema
>;

export const deletePatientResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeletePatientResponseSchema = z.infer<
  typeof deletePatientResponseSchema
>;

export async function deletePatientApi(
  params: DeletePatientParamsSchema,
): Promise<DeletePatientResponseSchema> {
  const response = await apiClient.delete<DeletePatientResponseSchema>(
    `/patients/${params.id}`,
  );
  return deletePatientResponseSchema.parse(response.data);
}

export function useDeletePatientMutation(params: DeletePatientParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-patient", params] as const;

  return useMutation<DeletePatientResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () => deletePatientApi(deletePatientParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["patients"] });
    },
    throwOnError: isAxiosError,
  });
}
