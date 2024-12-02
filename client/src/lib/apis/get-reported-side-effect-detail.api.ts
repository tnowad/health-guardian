import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { reportedSideEffectSchema } from "../schemas/reported-side-effect.schema";

export const getReportedSideEffectDetailResponseSchema =
  reportedSideEffectSchema;

export type GetReportedSideEffectDetailResponseSchema = z.infer<
  typeof getReportedSideEffectDetailResponseSchema
>;

export async function getReportedSideEffectDetailApi(
  id: string,
): Promise<GetReportedSideEffectDetailResponseSchema> {
  const response =
    await apiClient.get<GetReportedSideEffectDetailResponseSchema>(
      `/reported-side-effects/${id}`,
    );
  return getReportedSideEffectDetailResponseSchema.parse(response.data);
}

export function useGetReportedSideEffectDetailQuery(id: string) {
  const queryKey = ["reported-side-effect-detail", id] as const;
  return queryOptions<GetReportedSideEffectDetailResponseSchema>({
    queryKey,
    enabled: !!id,
    queryFn: () => getReportedSideEffectDetailApi(id),
  });
}
