import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { pastConditionSchema } from "../schemas/past-condition.schema";

export const createPastConditionBodySchema = pastConditionSchema.omit({
  id: true,
});

export type CreatePastConditionBodySchema = z.infer<
  typeof createPastConditionBodySchema
>;

export const createPastConditionResponseSchema = pastConditionSchema;

export type CreatePastConditionResponseSchema = z.infer<
  typeof createPastConditionResponseSchema
>;

export async function createPastConditionApi(
  body: CreatePastConditionBodySchema,
): Promise<CreatePastConditionResponseSchema> {
  const response =
    await apiClient.post<CreatePastConditionResponseSchema>(
      `/past-conditions`,
      body,
    );
  return createPastConditionResponseSchema.parse(response.data);
}

export function useCreatePastConditionMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-past-condition"] as const;

  return useMutation<
    CreatePastConditionResponseSchema,
    unknown,
    CreatePastConditionBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createPastConditionApi(
        createPastConditionBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["past-conditions"] });
    },
    throwOnError: isAxiosError,
  });
}


