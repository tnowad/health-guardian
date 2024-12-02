
import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteUserStaffParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteUserStaffParamsSchema = z.infer<typeof deleteUserStaffParamsSchema>;

export const deleteUserStaffResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteUserStaffResponseSchema = z.infer<typeof deleteUserStaffResponseSchema>;

export async function deleteUserStaffApi(
  params: DeleteUserStaffParamsSchema,
): Promise<DeleteUserStaffResponseSchema> {
  const response = await apiClient.delete<DeleteUserStaffResponseSchema>(
    `/user-staff/${params.id}`,
  );
  return deleteUserStaffResponseSchema.parse(response.data);
}

export function useDeleteUserStaffMutation(
  params: DeleteUserStaffParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-user-staff", params] as const;

  return useMutation<
    DeleteUserStaffResponseSchema,
    unknown,
    void
  >({
    mutationKey,
    mutationFn: () =>
      deleteUserStaffApi(deleteUserStaffParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-staffs"] });
    },
    throwOnError: isAxiosError,
  });
}
