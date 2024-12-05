import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { sideEffectSchema } from "../schemas/side-effect.schema";

export const createSideEffectBodySchema = sideEffectSchema.omit({
  id: true, // Omit the ID as it's generated on the backend
});

export type CreateSideEffectBodySchema = z.infer<
  typeof createSideEffectBodySchema
>;

export const createSideEffectResponseSchema = sideEffectSchema;

export type CreateSideEffectResponseSchema = z.infer<
  typeof createSideEffectResponseSchema
>;

export async function createSideEffectApi(
  body: CreateSideEffectBodySchema
): Promise<CreateSideEffectResponseSchema> {
  const response = await apiClient.post<CreateSideEffectResponseSchema>(
    `/side-effects`,
    body
  );
  return createSideEffectResponseSchema.parse(response.data);
}

export function useCreateSideEffectMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-side-effect"] as const;

  return useMutation<
    CreateSideEffectResponseSchema,
    unknown,
    CreateSideEffectBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createSideEffectApi(createSideEffectBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
