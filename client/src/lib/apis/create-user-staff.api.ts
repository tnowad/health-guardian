import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { userStaffSchema } from "../schemas/user-staff.schema";

export const createUserStaffBodySchema = userStaffSchema.omit({
  id: true, // Omit the ID as it's generated on the backend
});

export type CreateUserStaffBodySchema = z.infer<
  typeof createUserStaffBodySchema
>;

export const createUserStaffResponseSchema = userStaffSchema;

export type CreateUserStaffResponseSchema = z.infer<
  typeof createUserStaffResponseSchema
>;

export async function createUserStaffApi(
  body: CreateUserStaffBodySchema,
): Promise<CreateUserStaffResponseSchema> {
  const response = await apiClient.post<CreateUserStaffResponseSchema>(
    `/user-staff`,
    body,
  );
  return createUserStaffResponseSchema.parse(response.data);
}

export function useCreateUserStaffMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-user-staff"] as const;

  return useMutation<
    CreateUserStaffResponseSchema,
    unknown,
    CreateUserStaffBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createUserStaffApi(createUserStaffBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-staffs"] });
    },
    throwOnError: isAxiosError,
  });
}
