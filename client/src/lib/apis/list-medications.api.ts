import { z } from "zod";
import { medicationSchema } from "../schemas/medication.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listMedicationsQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  name: z.string().optional(),
  activeIngredient: z.string().optional(),
});
export type ListMedicationsQuerySchema = z.infer<
  typeof listMedicationsQuerySchema
>;

export const listMedicationsResponseSchema =
  createListResponseSchema(medicationSchema);
export type ListMedicationsResponseSchema = z.infer<
  typeof listMedicationsResponseSchema
>;

export const listMedicationsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListMedicationsErrorResponseSchema = z.infer<
  typeof listMedicationsErrorResponseSchema
>;

export async function listMedicationsApi(query: ListMedicationsQuerySchema) {
  const response = await apiClient.get<ListMedicationsResponseSchema>(
    "/medications",
    query,
  );
  return response.data;
}

export function createListMedicationsQueryOptions(
  query: ListMedicationsQuerySchema,
) {
  const queryKey = ["medications", query] as const;
  return queryOptions<ListMedicationsResponseSchema>({
    queryKey,
    queryFn: () => listMedicationsApi(listMedicationsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
