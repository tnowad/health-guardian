import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { userMedicalStaffSchema } from "../schemas/user-medical-staff.schema";

export const createUserMedicalStaffBodySchema = userMedicalStaffSchema.omit({
  id: true, // Omit the ID as it's generated on the backend
});

export type CreateUserMedicalStaffBodySchema = z.infer<
  typeof createUserMedicalStaffBodySchema
>;

export const createUserMedicalStaffResponseSchema = userMedicalStaffSchema;

export type CreateUserMedicalStaffResponseSchema = z.infer<
  typeof createUserMedicalStaffResponseSchema
>;

export async function createUserMedicalStaffApi(
  body: CreateUserMedicalStaffBodySchema,
): Promise<CreateUserMedicalStaffResponseSchema> {
  const response = await apiClient.post<CreateUserMedicalStaffResponseSchema>(
    `/user-medical-staff`,
    body,
  );
  return createUserMedicalStaffResponseSchema.parse(response.data);
}

export function useCreateUserMedicalStaffMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-user-medical-staff"] as const;

  return useMutation<
    CreateUserMedicalStaffResponseSchema,
    unknown,
    CreateUserMedicalStaffBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createUserMedicalStaffApi(createUserMedicalStaffBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-medical-staffs"] });
    },
    throwOnError: isAxiosError,
  });
}
