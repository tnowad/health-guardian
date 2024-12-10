import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { pastConditionSchema } from "../schemas/past-condition.schema";

export const updatePastConditionBodySchema = pastConditionSchema
  .omit({
    id: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdatePastConditionBodySchema = z.infer<typeof updatePastConditionBodySchema>;

export const updatePastConditionResponseSchema = pastConditionSchema;

export type UpdatePastConditionResponseSchema = z.infer<
  typeof updatePastConditionResponseSchema
>;

export async function updatePastConditionApi(
  body: UpdatePastConditionBodySchema,
): Promise<UpdatePastConditionResponseSchema> {
  const response = await apiClient.put<UpdatePastConditionResponseSchema>(
    `/past-conditions/${body.id}`,
    body,
  );
  return updatePastConditionResponseSchema.parse(response.data);
}

export function useUpdatePastConditionMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-past-condition"] as const;

  return useMutation<
    UpdatePastConditionResponseSchema,
    unknown,
    UpdatePastConditionBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updatePastConditionApi(updatePastConditionBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["past-conditions"] });
    },
    throwOnError: isAxiosError,
  });
}