import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { immunizationSchema } from "../schemas/immunization.schema";

export const updateImmunizationBodySchema = immunizationSchema
  .omit({
    id: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateImmunizationBodySchema = z.infer<typeof updateImmunizationBodySchema>;

export const updateImmunizationResponseSchema = immunizationSchema;

export type UpdateImmunizationResponseSchema = z.infer<
  typeof updateImmunizationResponseSchema
>;

export async function updateImmunizationApi(
  body: UpdateImmunizationBodySchema,
): Promise<UpdateImmunizationResponseSchema> {
  const response = await apiClient.put<UpdateImmunizationResponseSchema>(
    `/immunizations/${body.id}`,
    body,
  );
  return updateImmunizationResponseSchema.parse(response.data);
}

export function useUpdateImmunizationMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-immunization"] as const;

  return useMutation<
    UpdateImmunizationResponseSchema,
    unknown,
    UpdateImmunizationBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateImmunizationApi(updateImmunizationBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["immunizations"] });
    },
    throwOnError: isAxiosError,
  });
}

