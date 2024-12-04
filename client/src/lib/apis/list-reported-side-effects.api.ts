import { z } from "zod";
import { reportedSideEffectSchema } from "../schemas/reported-side-effect.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listReportedSideEffectsParamsSchema = z.object({
  patientId: z.string().uuid(),
});
export type ListReportedSideEffectsParamsSchema = z.infer<
  typeof listReportedSideEffectsParamsSchema
>;

export const listReportedSideEffectsQuerySchema = pageableRequestSchema.extend({
  prescriptionId: z.string().uuid().optional(),
  sideEffectId: z.string().uuid().optional(),
  severity: z.string().optional(), // e.g., "Mild", "Moderate", "Severe"
  outcome: z.string().optional(),
});
export type ListReportedSideEffectsQuerySchema = z.infer<
  typeof listReportedSideEffectsQuerySchema
>;

export const listReportedSideEffectsResponseSchema = createListResponseSchema(
  reportedSideEffectSchema,
);
export type ListReportedSideEffectsResponseSchema = z.infer<
  typeof listReportedSideEffectsResponseSchema
>;

export const listReportedSideEffectsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListReportedSideEffectsErrorResponseSchema = z.infer<
  typeof listReportedSideEffectsErrorResponseSchema
>;

export async function listReportedSideEffectsApi(
  params: ListReportedSideEffectsParamsSchema,
  query: ListReportedSideEffectsQuerySchema,
) {
  const response = await apiClient.get(
    `/patients/${params.patientId}/reported-side-effects`, query ,);
  return listReportedSideEffectsResponseSchema.parse(response.data);
}

export function createListReportedSideEffectsQueryOptions(
  params: ListReportedSideEffectsParamsSchema,
  query: ListReportedSideEffectsQuerySchema,
) {
  const queryKey = ["reported-side-effects", params, query] as const;
  return queryOptions<
    ListReportedSideEffectsResponseSchema,
    ListReportedSideEffectsErrorResponseSchema,
    ListReportedSideEffectsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listReportedSideEffectsApi(
        listReportedSideEffectsParamsSchema.parse(params),
        listReportedSideEffectsQuerySchema.parse(query),
      ),
    throwOnError: isAxiosError,
  });
}
