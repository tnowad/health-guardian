import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { consentFormSchema } from "../schemas/consent-form.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";

export const getConsentFormDetailResponseSchema = consentFormSchema;

export type GetConsentFormDetailResponseSchema = z.infer<
  typeof getConsentFormDetailResponseSchema
>;

export const getConsentFormDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetConsentFormDetailErrorResponseSchema = z.infer<
  typeof getConsentFormDetailErrorResponseSchema
>;

export async function getConsentFormDetailApi(
  id: string,
): Promise<GetConsentFormDetailResponseSchema> {
  const response = await apiClient.get<GetConsentFormDetailResponseSchema>(
    `/consent-forms/${id}`,
  );
  return getConsentFormDetailResponseSchema.parse(response.data);
}

export function useGetConsentFormDetailQuery(id: string) {
  const queryKey = ["consent-form-detail", id] as const;
  return queryOptions<
    GetConsentFormDetailResponseSchema,
    GetConsentFormDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getConsentFormDetailApi(id),
  });
}
