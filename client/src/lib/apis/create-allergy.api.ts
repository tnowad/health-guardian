import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { allergySchema } from "../schemas/allergy.schema";
import {encodePng} from "next/dist/server/lib/squoosh/impl";

export const createAllergyBodySchema = allergySchema.omit({
  id: true,
});

export type CreateAllergyBodySchema = z.infer<
  typeof createAllergyBodySchema
>;

export const createAllergyResponseSchema = allergySchema;

export type CreateAllergyResponseSchema = z.infer<
  typeof createAllergyResponseSchema
>;

export async function createAllergyApi(
  body: CreateAllergyBodySchema,
): Promise<CreateAllergyResponseSchema> {
  const response =
    await apiClient.post<CreateAllergyResponseSchema>(
      `/allergies`,
      body,
    );
  return createAllergyResponseSchema.parse(response.data);
}

export function useCreateAllergyMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-allergy"] as const;

  return useMutation<
    CreateAllergyResponseSchema,
    unknown,
    CreateAllergyBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createAllergyApi(
        createAllergyBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["allergies"] });
    },
    throwOnError: isAxiosError,
  });
}