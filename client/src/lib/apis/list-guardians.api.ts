import { z } from "zod";
import { guardianSchema } from "../schemas/guardian.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listGuardiansQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  name: z.string().optional(),
  relationshipToPatient: z.string().optional(),
});
export type ListGuardiansQuerySchema = z.infer<typeof listGuardiansQuerySchema>;

export const listGuardiansResponseSchema =
  createListResponseSchema(guardianSchema);
export type ListGuardiansResponseSchema = z.infer<
  typeof listGuardiansResponseSchema
>;

export const listGuardiansErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListGuardiansErrorResponseSchema = z.infer<
  typeof listGuardiansErrorResponseSchema
>;

export async function listGuardiansApi(query: ListGuardiansQuerySchema) {
  const response = await apiClient.get("/guardians", query);
  return listGuardiansResponseSchema.parse(response.data);
}

export function createListGuardiansQueryOptions(
  query: ListGuardiansQuerySchema,
) {
  const queryKey = ["guardians", query] as const;
  return queryOptions<
    ListGuardiansResponseSchema,
    ListGuardiansErrorResponseSchema,
    ListGuardiansQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () => listGuardiansApi(listGuardiansQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
