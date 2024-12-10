import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { allergySchema } from "../schemas/allergy.schema";

export const updateAllergyBodySchema = allergySchema
  .omit({
    id: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateAllergyBodySchema = z.infer<typeof updateAllergyBodySchema>;

export const updateAllergyResponseSchema = allergySchema;

export type UpdateAllergyResponseSchema = z.infer<
  typeof updateAllergyResponseSchema
>;

export async function updateAllergyApi(
  body: UpdateAllergyBodySchema,
): Promise<UpdateAllergyResponseSchema> {
  const response = await apiClient.put<UpdateAllergyResponseSchema>(
    `/allergies/${body.id}`,
    body,
  );
  return updateAllergyResponseSchema.parse(response.data);
}

export function useUpdateAllergyMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-allergy"] as const;

  return useMutation<
    UpdateAllergyResponseSchema,
    unknown,
    UpdateAllergyBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateAllergyApi(updateAllergyBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["allergies"] });
    },
    throwOnError: isAxiosError,
  });
}

