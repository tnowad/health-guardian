import { z } from "zod";
import { prescriptionSchema } from "../schemas/prescription.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listPrescriptionsQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  patientId: z.string().uuid().optional(),
  medicationId: z.string().uuid().optional(),
  prescribedById: z.string().uuid().optional(),
  status: z.enum(["active", "inactive"]).optional(),
});
export type ListPrescriptionsQuerySchema = z.infer<
  typeof listPrescriptionsQuerySchema
>;

export const listPrescriptionsResponseSchema =
  createListResponseSchema(prescriptionSchema);
export type ListPrescriptionsResponseSchema = z.infer<
  typeof listPrescriptionsResponseSchema
>;

export const listPrescriptionsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListPrescriptionsErrorResponseSchema = z.infer<
  typeof listPrescriptionsErrorResponseSchema
>;

export async function listPrescriptionsApi(
  query: ListPrescriptionsQuerySchema,
) {
  const response = await apiClient.get("/prescriptions", query);
  return listPrescriptionsResponseSchema.parse(response.data);
}

export function createListPrescriptionsQueryOptions(
  query: ListPrescriptionsQuerySchema,
) {
  const queryKey = ["prescriptions", query] as const;
  return queryOptions<ListPrescriptionsResponseSchema>({
    queryKey,
    queryFn: () =>
      listPrescriptionsApi(listPrescriptionsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
    enabled: !!query.patientId,
  });
}
