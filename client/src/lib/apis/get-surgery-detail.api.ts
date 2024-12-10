import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { surgerySchema } from "../schemas/surgery.schema";

export const getSurgeryDetailResponseSchema = surgerySchema;

export type GetSurgeryDetailResponseSchema = z.infer<
  typeof getSurgeryDetailResponseSchema
>;

export const getSurgeryDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetSurgeryDetailErrorResponseSchema = z.infer<
  typeof getSurgeryDetailErrorResponseSchema
>;

export async function getSurgeryDetailApi(
  id: string,
): Promise<GetSurgeryDetailResponseSchema> {
  const response = await apiClient.get<GetSurgeryDetailResponseSchema>(
    `/surgeries/${id}`,
  );
  return getSurgeryDetailResponseSchema.parse(response.data);
}

export function useGetSurgeryDetailQuery(id: string) {
  const queryKey = ["surgery-detail", id] as const;
  return queryOptions<
    GetSurgeryDetailResponseSchema,
    GetSurgeryDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getSurgeryDetailApi(id),
  });
}
