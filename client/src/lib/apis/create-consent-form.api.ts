import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { consentFormSchema } from "../schemas/consent-form.schema";

export const createConsentFormBodySchema = consentFormSchema.omit({
  id: true,
});

export type CreateConsentFormBodySchema = z.infer<
  typeof createConsentFormBodySchema
>;

export const createConsentFormResponseSchema = consentFormSchema;

export type CreateConsentFormResponseSchema = z.infer<
  typeof createConsentFormResponseSchema
>;

export async function createConsentFormApi(
  body: CreateConsentFormBodySchema,
): Promise<CreateConsentFormResponseSchema> {
  const response = await apiClient.post<CreateConsentFormResponseSchema>(
    `/consent-forms`,
    body,
  );
  return createConsentFormResponseSchema.parse(response.data);
}

export function useCreateConsentFormMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-consent-form"] as const;

  return useMutation<
    CreateConsentFormResponseSchema,
    unknown,
    CreateConsentFormBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createConsentFormApi(createConsentFormBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["consent-forms"] });
    },
    throwOnError: isAxiosError,
  });
}
