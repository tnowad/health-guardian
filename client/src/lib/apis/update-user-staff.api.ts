import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { userStaffSchema } from "../schemas/user-staff.schema";

export const updateUserStaffBodySchema = userStaffSchema;

export type UpdateUserStaffBodySchema = z.infer<
  typeof updateUserStaffBodySchema
>;

export const updateUserStaffResponseSchema = userStaffSchema;

export type UpdateUserStaffResponseSchema = z.infer<
  typeof updateUserStaffResponseSchema
>;

export async function updateUserStaffApi(
  body: UpdateUserStaffBodySchema,
): Promise<UpdateUserStaffResponseSchema> {
  const response = await apiClient.put<UpdateUserStaffResponseSchema>(
    `/user-staff/${body.id}`,
    body,
  );
  return updateUserStaffResponseSchema.parse(response.data);
}

export function useUpdateUserStaffMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-user-staff"] as const;

  return useMutation<
    UpdateUserStaffResponseSchema,
    unknown,
    UpdateUserStaffBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateUserStaffApi(updateUserStaffBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-staffs"] });
    },
    throwOnError: isAxiosError,
  });
}
