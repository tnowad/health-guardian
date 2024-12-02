import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { hospitalSchema } from "../schemas/hospital.schema";

export const getHospitalDetailResponseSchema = hospitalSchema;

export type GetHospitalDetailResponseSchema = z.infer<
  typeof getHospitalDetailResponseSchema
>;

export const getHospitalDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetHospitalDetailErrorResponseSchema = z.infer<
  typeof getHospitalDetailErrorResponseSchema
>;

export async function getHospitalDetailApi(
  id: string,
): Promise<GetHospitalDetailResponseSchema> {
  const response = await apiClient.get<GetHospitalDetailResponseSchema>(
    `/hospitals/${id}`,
  );
  return getHospitalDetailResponseSchema.parse(response.data);
}

export function useGetHospitalDetailQuery(id: string) {
  const queryKey = ["hospital-detail", id] as const;
  return queryOptions<
    GetHospitalDetailResponseSchema,
    GetHospitalDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getHospitalDetailApi(id),
  });
}
