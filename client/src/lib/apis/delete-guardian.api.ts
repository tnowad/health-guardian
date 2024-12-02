import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteGuardianParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteGuardianParamsSchema = z.infer<
  typeof deleteGuardianParamsSchema
>;

export const deleteGuardianResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteGuardianResponseSchema = z.infer<
  typeof deleteGuardianResponseSchema
>;

export async function deleteGuardianApi(
  params: DeleteGuardianParamsSchema,
): Promise<DeleteGuardianResponseSchema> {
  const response = await apiClient.delete<DeleteGuardianResponseSchema>(
    `/guardians/${params.id}`,
  );
  return deleteGuardianResponseSchema.parse(response.data);
}

export function useDeleteGuardianMutation(params: DeleteGuardianParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-guardian", params] as const;

  return useMutation<DeleteGuardianResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteGuardianApi(deleteGuardianParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["guardians"] });
    },
    throwOnError: isAxiosError,
  });
}
