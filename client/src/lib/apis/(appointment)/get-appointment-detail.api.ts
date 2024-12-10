import { apiClient } from "@/lib/client";
import { appointmentSchema } from "@/lib/schemas/(appointment)/appointment.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";

export const getAppointmentDetailResponseSchema = appointmentSchema;
export type GetAppointmentDetailResponseSchema = z.infer<
  typeof getAppointmentDetailResponseSchema
>;

export const getAppointmentDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type GetAppointmentDetailErrorResponseSchema = z.infer<
  typeof getAppointmentDetailErrorResponseSchema
>;

export async function getAppointmentDetailApi(
  appointmentId: string
): Promise<GetAppointmentDetailResponseSchema> {
  const response = await apiClient.get<GetAppointmentDetailResponseSchema>(
    `/appointments/${appointmentId}`
  );
  return getAppointmentDetailResponseSchema.parse(response.data);
}

export function useGetAppointmentDetailQuery(appointmentId: string) {
  const queryKey = ["appointment-detail", appointmentId] as const;
  return queryOptions<
    GetAppointmentDetailResponseSchema,
    GetAppointmentDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!appointmentId,
    queryFn: () => getAppointmentDetailApi(appointmentId),
  });
}
