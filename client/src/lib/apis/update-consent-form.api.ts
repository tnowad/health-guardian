import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { consentFormSchema } from "../schemas/consent-form.schema";

export const updateConsentFormBodySchema = consentFormSchema
  .omit({
    patientId: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateConsentFormBodySchema = z.infer<
  typeof updateConsentFormBodySchema
>;

export const updateConsentFormResponseSchema = consentFormSchema;

export type UpdateConsentFormResponseSchema = z.infer<
  typeof updateConsentFormResponseSchema
>;

export async function updateConsentFormApi(
  body: UpdateConsentFormBodySchema,
): Promise<UpdateConsentFormResponseSchema> {
  const response = await apiClient.put<UpdateConsentFormResponseSchema>(
    `/consent-forms/${body.id}`,
    body,
  );
  return updateConsentFormResponseSchema.parse(response.data);
}

export function useUpdateConsentFormMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-consent-form"] as const;

  return useMutation<
    UpdateConsentFormResponseSchema,
    unknown,
    UpdateConsentFormBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateConsentFormApi(updateConsentFormBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["consent-forms"] });
    },
    throwOnError: isAxiosError,
  });
}
