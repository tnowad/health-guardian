import { z } from "zod";
import { medicationSchema } from "../schemas/medication.schema";
import { apiClient } from "../client";
import { keepPreviousData, queryOptions } from "@tanstack/react-query";
export const getListMedicationQueryFilterSchema = z.object({
  ids: z.string().uuid(),
  name: z.string(),
  activeIngredient: z.string().optional(),
  dosageForm: z.string().optional(),
  standardDosage: z.string().optional(),
  manufacturer: z.string().optional(),
});

export const getListMedicationQuerySchema = z.object({
  query: z.string().optional(),
});

export const getListMedicationsResponseSchema = z.object({
  items: z.array(medicationSchema),
  rowCount: z.number(),
});
export type ListMedicationResponseSchema = z.infer<
  typeof getListMedicationsResponseSchema
>;

export const getListMedicationsErrorResponseSchema = z.object({
  message: z.string(),
});

export type ListMedicationErrorResponseSchema = z.infer<
  typeof getListMedicationsErrorResponseSchema
>;

export async function getListMedicationsApi(): Promise<ListMedicationResponseSchema> {
  const response =
    await apiClient.get<ListMedicationResponseSchema>("/medications");
  return response.data;
}
export function createListMedicationsQueryOptions({ }) {
  const queryKey = ["get-medications"] as const;
  return queryOptions<
    ListMedicationResponseSchema,
    ListMedicationErrorResponseSchema
  >({
    queryKey,
    queryFn: () => getListMedicationsApi(),
    placeholderData: keepPreviousData,
  });
}
