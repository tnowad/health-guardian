import { z } from "zod";
import { appointmentSchema } from "../schemas/appointment.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listAppointmentsQuerySchema = pageableRequestSchema.extend({
  doctorId: z.string().uuid().optional(),
  appointmentDate: z.string().optional(),
  status: z.string().optional(),
});
export type ListAppointmentsQuerySchema = z.infer<
  typeof listAppointmentsQuerySchema
>;

export const listAppointmentsResponseSchema =
  createListResponseSchema(appointmentSchema);
export type ListAppointmentsResponseSchema = z.infer<
  typeof listAppointmentsResponseSchema
>;

export const listAppointmentsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListAppointmentsErrorResponseSchema = z.infer<
  typeof listAppointmentsErrorResponseSchema
>;

export async function listAppointmentsApi(query: ListAppointmentsQuerySchema) {
  const response = await apiClient.get("/appointments",  query);
  return listAppointmentsResponseSchema.parse(response.data);
}

export function createListAppointmentsQueryOptions(
  query: ListAppointmentsQuerySchema,
) {
  const queryKey = ["appointments", query] as const;
  return queryOptions<
    ListAppointmentsResponseSchema,
    ListAppointmentsErrorResponseSchema,
    ListAppointmentsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listAppointmentsApi(listAppointmentsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
