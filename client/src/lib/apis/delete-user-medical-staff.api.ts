import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteUserMedicalStaffParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteUserMedicalStaffParamsSchema = z.infer<
  typeof deleteUserMedicalStaffParamsSchema
>;

export const deleteUserMedicalStaffResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteUserMedicalStaffResponseSchema = z.infer<
  typeof deleteUserMedicalStaffResponseSchema
>;

export async function deleteUserMedicalStaffApi(
  params: DeleteUserMedicalStaffParamsSchema,
): Promise<DeleteUserMedicalStaffResponseSchema> {
  const response = await apiClient.delete<DeleteUserMedicalStaffResponseSchema>(
    `/user-medical-staff/${params.id}`,
  );
  return deleteUserMedicalStaffResponseSchema.parse(response.data);
}

export function useDeleteUserMedicalStaffMutation(
  params: DeleteUserMedicalStaffParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-user-medical-staff", params] as const;

  return useMutation<DeleteUserMedicalStaffResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteUserMedicalStaffApi(
        deleteUserMedicalStaffParamsSchema.parse(params),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-medical-staffs"] });
    },
    throwOnError: isAxiosError,
  });
}
