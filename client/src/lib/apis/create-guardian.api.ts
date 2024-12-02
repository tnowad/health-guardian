import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { guardianSchema } from "../schemas/guardian.schema";

export const createGuardianBodySchema = guardianSchema.omit({
  id: true,
});

export type CreateGuardianBodySchema = z.infer<typeof createGuardianBodySchema>;

export const createGuardianResponseSchema = guardianSchema;

export type CreateGuardianResponseSchema = z.infer<
  typeof createGuardianResponseSchema
>;

export async function createGuardianApi(
  body: CreateGuardianBodySchema,
): Promise<CreateGuardianResponseSchema> {
  const response = await apiClient.post<CreateGuardianResponseSchema>(
    `/guardians`,
    body,
  );
  return createGuardianResponseSchema.parse(response.data);
}

export function useCreateGuardianMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-guardian"] as const;

  return useMutation<
    CreateGuardianResponseSchema,
    unknown,
    CreateGuardianBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createGuardianApi(createGuardianBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["guardians"] });
    },
    throwOnError: isAxiosError,
  });
}
