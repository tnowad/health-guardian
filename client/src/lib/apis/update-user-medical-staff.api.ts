import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { userMedicalStaffSchema } from "../schemas/user-medical-staff.schema";

export const updateUserMedicalStaffBodySchema = userMedicalStaffSchema;

export type UpdateUserMedicalStaffBodySchema = z.infer<
  typeof updateUserMedicalStaffBodySchema
>;

export const updateUserMedicalStaffResponseSchema = userMedicalStaffSchema;

export type UpdateUserMedicalStaffResponseSchema = z.infer<
  typeof updateUserMedicalStaffResponseSchema
>;

export async function updateUserMedicalStaffApi(
  body: UpdateUserMedicalStaffBodySchema,
): Promise<UpdateUserMedicalStaffResponseSchema> {
  const response = await apiClient.put<UpdateUserMedicalStaffResponseSchema>(
    `/user-medical-staff/${body.id}`,
    body,
  );
  return updateUserMedicalStaffResponseSchema.parse(response.data);
}

export function useUpdateUserMedicalStaffMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-user-medical-staff"] as const;

  return useMutation<
    UpdateUserMedicalStaffResponseSchema,
    unknown,
    UpdateUserMedicalStaffBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateUserMedicalStaffApi(updateUserMedicalStaffBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-medical-staffs"] });
    },
    throwOnError: isAxiosError,
  });
}
