import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { patientLogSchema } from "../schemas/patient-log.schema";

export const getPatientLogDetailResponseSchema = patientLogSchema;

export type GetPatientLogDetailResponseSchema = z.infer<
  typeof getPatientLogDetailResponseSchema
>;

export const getPatientLogDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetPatientLogDetailErrorResponseSchema = z.infer<
  typeof getPatientLogDetailErrorResponseSchema
>;

export async function getPatientLogDetailApi(
  id: string,
): Promise<GetPatientLogDetailResponseSchema> {
  const response = await apiClient.get<GetPatientLogDetailResponseSchema>(
    `/patient-logs/${id}`,
  );
  return getPatientLogDetailResponseSchema.parse(response.data);
}

export function useGetPatientLogDetailQuery(id: string) {
  const queryKey = ["patient-log-detail", id] as const;
  return queryOptions<
    GetPatientLogDetailResponseSchema,
    GetPatientLogDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getPatientLogDetailApi(id),
  });
}

