import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteConsentFormParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteConsentFormParamsSchema = z.infer<
  typeof deleteConsentFormParamsSchema
>;

export const deleteConsentFormResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteConsentFormResponseSchema = z.infer<
  typeof deleteConsentFormResponseSchema
>;

export async function deleteConsentFormApi(
  params: DeleteConsentFormParamsSchema,
): Promise<DeleteConsentFormResponseSchema> {
  const response = await apiClient.delete<DeleteConsentFormResponseSchema>(
    `/consent-forms/${params.id}`,
  );
  return deleteConsentFormResponseSchema.parse(response.data);
}

export function useDeleteConsentFormMutation(
  params: DeleteConsentFormParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-consent-form", params] as const;

  return useMutation<DeleteConsentFormResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteConsentFormApi(deleteConsentFormParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["consent-forms"] });
    },
    throwOnError: isAxiosError,
  });
}
