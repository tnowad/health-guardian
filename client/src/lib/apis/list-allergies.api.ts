import { z } from "zod";
import { allergySchema } from "../schemas/allergy.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listAllergiesQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  userId: z.string().uuid(), 
    vaccinationDate: z.string().datetime().optional(), 
    vaccineName: z.string().optional(), 
    batchNumber: z.string().optional(), 
    notes: z.string().optional(), 
});

export type ListAllergiesQuerySchema = z.infer<typeof listAllergiesQuerySchema>;

export const listAllergiesResponseSchema =
  createListResponseSchema(allergySchema);

export type ListAllergiesResponseSchema = z.infer<
  typeof listAllergiesResponseSchema
>;

export const listAllergiesErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);

export type ListAllergiesErrorResponseSchema = z.infer<
  typeof listAllergiesErrorResponseSchema
>;

export async function listAllergiesApi(query: ListAllergiesQuerySchema) {
  const response = await apiClient.get<ListAllergiesResponseSchema>(
    "/allergies",
    query,
  );
  return response.data;
}

export function createListAllergiesQueryOptions(
  query: ListAllergiesQuerySchema,
) {
  const queryKey = ["allergies", query] as const;
  return queryOptions<ListAllergiesResponseSchema>({
    queryKey,
    queryFn: () => listAllergiesApi(listAllergiesQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
