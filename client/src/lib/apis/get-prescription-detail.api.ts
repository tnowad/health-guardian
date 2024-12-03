import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { prescriptionSchema } from "../schemas/prescription.schema";

export const getPrescriptionDetailResponseSchema = prescriptionSchema;

export type GetPrescriptionDetailResponseSchema = z.infer<
  typeof getPrescriptionDetailResponseSchema
>;

export const getPrescriptionDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetPrescriptionDetailErrorResponseSchema = z.infer<
  typeof getPrescriptionDetailErrorResponseSchema
>;

export async function getPrescriptionDetailApi(
  id: string,
): Promise<GetPrescriptionDetailResponseSchema> {
  const response = await apiClient.get<GetPrescriptionDetailResponseSchema>(
    `/prescriptions/${id}`,
  );
  return getPrescriptionDetailResponseSchema.parse(response.data);
}

export function useGetPrescriptionDetailQuery(id: string) {
  const queryKey = ["prescription-detail", id] as const;
  return queryOptions<
    GetPrescriptionDetailResponseSchema,
    GetPrescriptionDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getPrescriptionDetailApi(id),
  });
}

