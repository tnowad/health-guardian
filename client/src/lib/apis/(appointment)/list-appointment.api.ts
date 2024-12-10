import { z } from "zod";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "@/lib/schemas/page.schema";
import { appointmentSchema } from "@/lib/schemas/(appointment)/appointment.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";

export const listAppointmentsQuerySchema = pageableRequestSchema.extend({
  userId: z.string().uuid().optional(),
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
  [unauthorizedResponseSchema]
);
export type ListAppointmentsErrorResponseSchema = z.infer<
  typeof listAppointmentsErrorResponseSchema
>;

export async function listAppointmentsApi(query: ListAppointmentsQuerySchema) {
  const response = await apiClient.get<ListAppointmentsResponseSchema>(
    "/appointments",
    query
  );
  return response.data;
}

export function createListAppointmentsQueryOptions(
  query: ListAppointmentsQuerySchema
) {
  const queryKey = ["appointments", query] as const;
  return queryOptions<ListAppointmentsResponseSchema>({
    queryKey,
    queryFn: () =>
      listAppointmentsApi(listAppointmentsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
