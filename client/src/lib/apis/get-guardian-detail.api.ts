import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { guardianSchema } from "../schemas/guardian.schema";

export const getGuardianDetailResponseSchema = guardianSchema;

export type GetGuardianDetailResponseSchema = z.infer<
  typeof getGuardianDetailResponseSchema
>;

export const getGuardianDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetGuardianDetailErrorResponseSchema = z.infer<
  typeof getGuardianDetailErrorResponseSchema
>;

export async function getGuardianDetailApi(
  id: string,
): Promise<GetGuardianDetailResponseSchema> {
  const response = await apiClient.get<GetGuardianDetailResponseSchema>(
    `/guardians/${id}`,
  );
  return getGuardianDetailResponseSchema.parse(response.data);
}

export function useGetGuardianDetailQuery(id: string) {
  const queryKey = ["guardian-detail", id] as const;
  return queryOptions<
    GetGuardianDetailResponseSchema,
    GetGuardianDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getGuardianDetailApi(id),
  });
}
