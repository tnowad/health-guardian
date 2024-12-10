import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { immunizationSchema } from "../schemas/immunization.schema";

export const getImmunizationDetailResponseSchema = immunizationSchema;

export type GetImmunizationDetailResponseSchema = z.infer<
  typeof getImmunizationDetailResponseSchema
>;

export const getImmunizationDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetImmunizationDetailErrorResponseSchema = z.infer<
  typeof getImmunizationDetailErrorResponseSchema
>;

export async function getImmunizationDetailApi(
  id: string,
): Promise<GetImmunizationDetailResponseSchema> {
  const response = await apiClient.get<GetImmunizationDetailResponseSchema>(
    `/immunizations/${id}`,
  );
  return getImmunizationDetailResponseSchema.parse(response.data);
}

export function useGetImmunizationDetailQuery(id: string) {
  const queryKey = ["immunization-detail", id] as const;
  return queryOptions<
    GetImmunizationDetailResponseSchema,
    GetImmunizationDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getImmunizationDetailApi(id),
  });
}