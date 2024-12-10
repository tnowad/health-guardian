import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { allergySchema } from "../schemas/allergy.schema";

export const getAllergyDetailResponseSchema = allergySchema;

export type GetAllergyDetailResponseSchema = z.infer<
  typeof getAllergyDetailResponseSchema
>;

export const getAllergyDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetAllergyDetailErrorResponseSchema = z.infer<
  typeof getAllergyDetailErrorResponseSchema
>;

export async function getAllergyDetailApi(
  id: string,
): Promise<GetAllergyDetailResponseSchema> {
  const response = await apiClient.get<GetAllergyDetailResponseSchema>(
    `/allergies/${id}`,
  );
  return getAllergyDetailResponseSchema.parse(response.data);
}

export function useGetAllergyDetailQuery(id: string) {
  const queryKey = ["allergy-detail", id] as const;
  return queryOptions<
    GetAllergyDetailResponseSchema,
    GetAllergyDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getAllergyDetailApi(id),
  });
}

