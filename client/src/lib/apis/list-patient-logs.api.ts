import { z } from "zod";
import { patientLogSchema } from "../schemas/patient-log.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listPatientLogsQuerySchema = pageableRequestSchema.extend({
  actionType: z.string().optional(),
  logDate: z.string().optional(),
});
export type ListPatientLogsQuerySchema = z.infer<
  typeof listPatientLogsQuerySchema
>;

export const listPatientLogsResponseSchema =
  createListResponseSchema(patientLogSchema);
export type ListPatientLogsResponseSchema = z.infer<
  typeof listPatientLogsResponseSchema
>;

export const listPatientLogsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListPatientLogsErrorResponseSchema = z.infer<
  typeof listPatientLogsErrorResponseSchema
>;

export async function listPatientLogsApi(query: ListPatientLogsQuerySchema) {
  const response = await apiClient.get("/patient-logs", query);
  return response.data;
}

export function createListPatientLogsQueryOptions(
  query: ListPatientLogsQuerySchema,
) {
  const queryKey = ["patient-logs", query] as const;
  return queryOptions<ListPatientLogsResponseSchema>({
    queryKey,
    queryFn: () => listPatientLogsApi(listPatientLogsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
