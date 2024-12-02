import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { guardianSchema } from "../schemas/guardian.schema";

export const updateGuardianBodySchema = guardianSchema
  .omit({
    id: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateGuardianBodySchema = z.infer<typeof updateGuardianBodySchema>;

export const updateGuardianResponseSchema = guardianSchema;

export type UpdateGuardianResponseSchema = z.infer<
  typeof updateGuardianResponseSchema
>;

export async function updateGuardianApi(
  body: UpdateGuardianBodySchema,
): Promise<UpdateGuardianResponseSchema> {
  const response = await apiClient.put<UpdateGuardianResponseSchema>(
    `/guardians/${body.id}`,
    body,
  );
  return updateGuardianResponseSchema.parse(response.data);
}

export function useUpdateGuardianMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-guardian"] as const;

  return useMutation<
    UpdateGuardianResponseSchema,
    unknown,
    UpdateGuardianBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateGuardianApi(updateGuardianBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["guardians"] });
    },
    throwOnError: isAxiosError,
  });
}
