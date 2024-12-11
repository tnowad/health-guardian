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
  userId: z.string().uuid().optional(),
  issuedBy: z.string().uuid().optional(),
  validUntil: z.string().optional(),
  startDate: z.string().optional(),
  endDate: z.string().optional(),
  status: z.enum(["ACTIVE", "COMPLETED", "EXPIRED"]).optional(),
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
  const response = await apiClient.get<ListPrescriptionsResponseSchema>(
    "/prescriptions",
    query,
  );
  return response.data;
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
    enabled: !!query.userId,
  });
}
