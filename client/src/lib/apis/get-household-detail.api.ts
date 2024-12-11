import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { householdSchema } from "../schemas/household.schema";

export const getHouseholdDetailResponseSchema = householdSchema;

export type GetHouseholdDetailResponseSchema = z.infer<
  typeof getHouseholdDetailResponseSchema
>;

export const getHouseholdDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetHouseholdDetailErrorResponseSchema = z.infer<
  typeof getHouseholdDetailErrorResponseSchema
>;

export async function getHouseholdDetailApi(
  id: string,
): Promise<GetHouseholdDetailResponseSchema> {
  const response = await apiClient.get<GetHouseholdDetailResponseSchema>(
    `/households/${id}`,
  );
  return response.data;
}

export function createGetHouseholdDetailQueryOptions(id: string) {
  const queryKey = ["household-detail", id] as const;
  return queryOptions<GetHouseholdDetailResponseSchema>({
    queryKey,
    queryFn: () => getHouseholdDetailApi(id),
  });
}
