import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { immunizationSchema } from "../schemas/immunization.schema";

export const createImmunizationBodySchema = immunizationSchema.omit({
  id: true,
});

export type CreateImmunizationBodySchema = z.infer<
  typeof createImmunizationBodySchema
>;

export const createImmunizationResponseSchema = immunizationSchema;

export type CreateImmunizationResponseSchema = z.infer<
  typeof createImmunizationResponseSchema
>;

export async function createImmunizationApi(
  body: CreateImmunizationBodySchema,
): Promise<CreateImmunizationResponseSchema> {
  const response =
    await apiClient.post<CreateImmunizationResponseSchema>(
      `/immunizations`,
      body,
    );
  return createImmunizationResponseSchema.parse(response.data);
}

export function useCreateImmunizationMutation() {
    const queryClient = getQueryClient();
    const mutationKey = ["create-immunization"] as const;

    return useMutation<
        CreateImmunizationResponseSchema,
        unknown,
        CreateImmunizationBodySchema
    >({
        mutationKey,
        mutationFn: (body) =>
        createImmunizationApi(
            createImmunizationBodySchema.parse(body),
        ),
        onSuccess() {
        queryClient.invalidateQueries({ queryKey: ["immunizations"] });
        },
        throwOnError: isAxiosError,
    });
}