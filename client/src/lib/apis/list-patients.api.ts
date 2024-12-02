import { z } from "zod";
import { patientSchema } from "../schemas/patient.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listPatientsQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  medicalStatus: z.string().optional(),
  gender: z.string().optional(),
});
export type ListPatientsQuerySchema = z.infer<typeof listPatientsQuerySchema>;

export const listPatientsResponseSchema =
  createListResponseSchema(patientSchema);
export type ListPatientsResponseSchema = z.infer<
  typeof listPatientsResponseSchema
>;

export const listPatientsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListPatientsErrorResponseSchema = z.infer<
  typeof listPatientsErrorResponseSchema
>;

export async function listPatientsApi(query: ListPatientsQuerySchema) {
  const response = await apiClient.get("/patients", query);
  return listPatientsResponseSchema.parse(response.data);
}

export function createListPatientsQueryOptions(query: ListPatientsQuerySchema) {
  const queryKey = ["patients", query] as const;
  return queryOptions<
    ListPatientsResponseSchema,
    ListPatientsErrorResponseSchema,
    ListPatientsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () => listPatientsApi(listPatientsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
