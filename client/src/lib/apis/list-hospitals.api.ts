import { z } from "zod";
import { hospitalSchema } from "../schemas/hospital.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listHospitalsQuerySchema = pageableRequestSchema.extend({
  name: z.string().optional(),
  location: z.string().optional(),
});
export type ListHospitalsQuerySchema = z.infer<typeof listHospitalsQuerySchema>;

export const listHospitalsResponseSchema =
  createListResponseSchema(hospitalSchema);
export type ListHospitalsResponseSchema = z.infer<
  typeof listHospitalsResponseSchema
>;

export const listHospitalsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListHospitalsErrorResponseSchema = z.infer<
  typeof listHospitalsErrorResponseSchema
>;

export async function listHospitalsApi(query: ListHospitalsQuerySchema) {
  const response = await apiClient.get("/hospitals", { params: query });
  return listHospitalsResponseSchema.parse(response.data);
}

export function createListHospitalsQueryOptions(
  query: ListHospitalsQuerySchema,
) {
  const queryKey = ["hospitals", query] as const;
  return queryOptions<
    ListHospitalsResponseSchema,
    ListHospitalsErrorResponseSchema,
    ListHospitalsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () => listHospitalsApi(listHospitalsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
