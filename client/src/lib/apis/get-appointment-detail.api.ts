import { queryOptions, useQuery } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { appointmentSchema } from "../schemas/appointment.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";

// Output schema: Phản hồi sẽ chứa toàn bộ chi tiết lịch hẹn
export const getAppointmentDetailResponseSchema = appointmentSchema;

// TypeScript type cho output
export type GetAppointmentDetailResponseSchema = z.infer<
  typeof getAppointmentDetailResponseSchema
>;

// Error schema: Định nghĩa lỗi
export const getAppointmentDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

// TypeScript type cho lỗi
export type GetAppointmentDetailErrorResponseSchema = z.infer<
  typeof getAppointmentDetailErrorResponseSchema
>;

// Hàm gọi API lấy chi tiết lịch hẹn
export async function getAppointmentDetailApi(
  id: string,
): Promise<GetAppointmentDetailResponseSchema> {
  const response = await apiClient.get<GetAppointmentDetailResponseSchema>(
    `/appointments/${id}`,
  );
  return getAppointmentDetailResponseSchema.parse(response.data);
}

export function useGetAppointmentDetailQuery(id: string) {
  const queryKey = ["appointment-detail", id] as const;
  return queryOptions<
    GetAppointmentDetailResponseSchema,
    GetAppointmentDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getAppointmentDetailApi(id),
  });
}
