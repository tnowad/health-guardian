import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { sideEffectSchema } from "../schemas/side-effect.schema";

export const updateSideEffectBodySchema = sideEffectSchema;

export type UpdateSideEffectBodySchema = z.infer<
  typeof updateSideEffectBodySchema
>;

export const updateSideEffectResponseSchema = sideEffectSchema;

export type UpdateSideEffectResponseSchema = z.infer<
  typeof updateSideEffectResponseSchema
>;

export async function updateSideEffectApi(
  body: UpdateSideEffectBodySchema,
): Promise<UpdateSideEffectResponseSchema> {
  const response = await apiClient.put<UpdateSideEffectResponseSchema>(
    `/side-effects/${body.id}`,
    body,
  );
  return updateSideEffectResponseSchema.parse(response.data);
}

export function useUpdateSideEffectMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-side-effect"] as const;

  return useMutation<
    UpdateSideEffectResponseSchema,
    unknown,
    UpdateSideEffectBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateSideEffectApi(updateSideEffectBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
