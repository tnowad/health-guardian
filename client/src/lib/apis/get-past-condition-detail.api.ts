import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { pastConditionSchema } from "../schemas/past-condition.schema";

export const getPastConditionDetailResponseSchema = pastConditionSchema;

export type GetPastConditionDetailResponseSchema = z.infer<
  typeof getPastConditionDetailResponseSchema
>;

export const getPastConditionDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetPastConditionDetailErrorResponseSchema = z.infer<
  typeof getPastConditionDetailErrorResponseSchema
>;

export async function getPastConditionDetailApi(
  id: string,
): Promise<GetPastConditionDetailResponseSchema> {
  const response = await apiClient.get<GetPastConditionDetailResponseSchema>(
    `/past-conditions/${id}`,
  );
  return getPastConditionDetailResponseSchema.parse(response.data);
}

export function useGetPastConditionDetailQuery(id: string) {
  const queryKey = ["past-condition-detail", id] as const;
  return queryOptions<
    GetPastConditionDetailResponseSchema,
    GetPastConditionDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getPastConditionDetailApi(id),
  });
}
