import { z } from "zod";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { prescriptionItemSchema } from "../schemas/prescription-item.schema";

// Define the query schema for listing prescription items
export const listPrescriptionItemsQuerySchema = pageableRequestSchema.extend({
  prescriptionId: z.string().uuid().optional(),
  userId: z.string().uuid().optional(),
  status: z.enum(["PENDING", "TAKEN", "MISSED"]).optional(),
});
export type ListPrescriptionItemsQuerySchema = z.infer<
  typeof listPrescriptionItemsQuerySchema
>;

// Define the response schema for listing prescription items
export const listPrescriptionItemsResponseSchema = createListResponseSchema(
  prescriptionItemSchema,
);
export type ListPrescriptionItemsResponseSchema = z.infer<
  typeof listPrescriptionItemsResponseSchema
>;

// Define the error response schema for listing prescription items
export const listPrescriptionItemsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListPrescriptionItemsErrorResponseSchema = z.infer<
  typeof listPrescriptionItemsErrorResponseSchema
>;

// API function to fetch prescription items
export async function listPrescriptionItemsApi(
  query: ListPrescriptionItemsQuerySchema,
) {
  const response = await apiClient.get<ListPrescriptionItemsResponseSchema>(
    "/prescription-items",
    query,
  );
  return response.data;
}

// Create query options for listing prescription items
export function createListPrescriptionItemsQueryOptions(
  query: ListPrescriptionItemsQuerySchema,
) {
  const queryKey = ["prescriptionItems", query] as const;
  return queryOptions<ListPrescriptionItemsResponseSchema>({
    queryKey,
    queryFn: () =>
      listPrescriptionItemsApi(listPrescriptionItemsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
    enabled: !!query.prescriptionId || !!query.userId,
  });
}
