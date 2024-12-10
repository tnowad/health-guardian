import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteSurgeryParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteSurgeryParamsSchema = z.infer<
  typeof deleteSurgeryParamsSchema
>;

export const deleteSurgeryResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteSurgeryResponseSchema = z.infer<
  typeof deleteSurgeryResponseSchema
>;

export async function deleteSurgeryApi(
  params: DeleteSurgeryParamsSchema,
): Promise<DeleteSurgeryResponseSchema> {
  const response = await apiClient.delete<DeleteSurgeryResponseSchema>(
    `/surgeries/${params.id}`,
  );
  return deleteSurgeryResponseSchema.parse(response.data);
}

export function useDeleteSurgeryMutation(params: DeleteSurgeryParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-surgery", params] as const;

  return useMutation<DeleteSurgeryResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteSurgeryApi(deleteSurgeryParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["surgeries"] });
    },
    throwOnError: isAxiosError,
  });
}