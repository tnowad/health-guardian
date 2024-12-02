import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";

export const getHouseholdDetailResponseSchema = z.object({
  id: z.string().uuid(),
  head: z.string().uuid(),
});

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
  return getHouseholdDetailResponseSchema.parse(response.data);
}

export function useGetHouseholdDetailQuery(id: string) {
  const queryKey = ["household-detail", id] as const;
  return queryOptions<
    GetHouseholdDetailResponseSchema,
    GetHouseholdDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getHouseholdDetailApi(id),
  });
}
