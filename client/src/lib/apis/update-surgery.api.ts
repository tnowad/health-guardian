import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { surgerySchema } from "../schemas/surgery.schema";

export const updateSurgeryBodySchema = surgerySchema
  .omit({
    id: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateSurgeryBodySchema = z.infer<typeof updateSurgeryBodySchema>;

export const updateSurgeryResponseSchema = surgerySchema;

export type UpdateSurgeryResponseSchema = z.infer<
  typeof updateSurgeryResponseSchema
>;

export async function updateSurgeryApi(
  body: UpdateSurgeryBodySchema,
): Promise<UpdateSurgeryResponseSchema> {
  const response = await apiClient.put<UpdateSurgeryResponseSchema>(
    `/surgeries/${body.id}`,
    body,
  );
  return updateSurgeryResponseSchema.parse(response.data);
}

export function useUpdateSurgeryMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-surgery"] as const;

  return useMutation<
    UpdateSurgeryResponseSchema,
    unknown,
    UpdateSurgeryBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateSurgeryApi(updateSurgeryBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["surgeries"] });
    },
    throwOnError: isAxiosError,
  });
}