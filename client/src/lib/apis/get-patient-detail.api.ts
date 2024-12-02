import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { patientSchema } from "../schemas/patient.schema";

export const getPatientDetailResponseSchema = patientSchema;

export type GetPatientDetailResponseSchema = z.infer<
  typeof getPatientDetailResponseSchema
>;

export async function getPatientDetailApi(
  id: string,
): Promise<GetPatientDetailResponseSchema> {
  const response = await apiClient.get<GetPatientDetailResponseSchema>(
    `/patients/${id}`,
  );
  return getPatientDetailResponseSchema.parse(response.data);
}

export function useGetPatientDetailQuery(id: string) {
  const queryKey = ["patient-detail", id] as const;
  return queryOptions<GetPatientDetailResponseSchema>({
    queryKey,
    enabled: !!id,
    queryFn: () => getPatientDetailApi(id),
  });
}
