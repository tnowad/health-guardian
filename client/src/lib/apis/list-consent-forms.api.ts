import { z } from "zod";
import { consentFormSchema } from "../schemas/consent-form.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listConsentFormsQuerySchema = pageableRequestSchema.extend({
  consentStatus: z.string().optional(),
  consentDate: z.string().optional(),
});
export type ListConsentFormsQuerySchema = z.infer<
  typeof listConsentFormsQuerySchema
>;

export const listConsentFormsResponseSchema =
  createListResponseSchema(consentFormSchema);
export type ListConsentFormsResponseSchema = z.infer<
  typeof listConsentFormsResponseSchema
>;

export const listConsentFormsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListConsentFormsErrorResponseSchema = z.infer<
  typeof listConsentFormsErrorResponseSchema
>;

export async function listConsentFormsApi(query: ListConsentFormsQuerySchema) {
  const response = await apiClient.get("/consent-forms", { params: query });
  return listConsentFormsResponseSchema.parse(response.data);
}

export function createListConsentFormsQueryOptions(
  query: ListConsentFormsQuerySchema,
) {
  const queryKey = ["consent-forms", query] as const;
  return queryOptions<
    ListConsentFormsResponseSchema,
    ListConsentFormsErrorResponseSchema,
    ListConsentFormsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listConsentFormsApi(listConsentFormsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
