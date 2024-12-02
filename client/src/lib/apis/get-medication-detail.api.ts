import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { medicationSchema } from "../schemas/medication.schema";

export const getMedicationDetailResponseSchema = medicationSchema;

export type GetMedicationDetailResponseSchema = z.infer<
  typeof getMedicationDetailResponseSchema
>;

export const getMedicationDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetMedicationDetailErrorResponseSchema = z.infer<
  typeof getMedicationDetailErrorResponseSchema
>;

export async function getMedicationDetailApi(
  id: string,
): Promise<GetMedicationDetailResponseSchema> {
  const response = await apiClient.get<GetMedicationDetailResponseSchema>(
    `/medications/${id}`,
  );
  return getMedicationDetailResponseSchema.parse(response.data);
}

export function useGetMedicationDetailQuery(id: string) {
  const queryKey = ["medication-detail", id] as const;
  return queryOptions<
    GetMedicationDetailResponseSchema,
    GetMedicationDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getMedicationDetailApi(id),
  });
}
