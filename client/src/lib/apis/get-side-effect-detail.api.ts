import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { sideEffectSchema } from "../schemas/side-effect.schema";

export const getSideEffectDetailResponseSchema = sideEffectSchema;

export type GetSideEffectDetailResponseSchema = z.infer<
  typeof getSideEffectDetailResponseSchema
>;

export async function getSideEffectDetailApi(
  id: string,
): Promise<GetSideEffectDetailResponseSchema> {
  const response = await apiClient.get<GetSideEffectDetailResponseSchema>(
    `/side-effects/${id}`,
  );
  return getSideEffectDetailResponseSchema.parse(response.data);
}

export function useGetSideEffectDetailQuery(id: string) {
  const queryKey = ["side-effect-detail", id] as const;
  return queryOptions<GetSideEffectDetailResponseSchema>({
    queryKey,
    enabled: !!id,
    queryFn: () => getSideEffectDetailApi(id),
  });
}
