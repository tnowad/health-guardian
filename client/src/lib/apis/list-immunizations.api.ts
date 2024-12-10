import { z } from "zod";
import { immunizationSchema } from "../schemas/immunization.schema";
import {
    createListResponseSchema,
    pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listImmunizationsQuerySchema = pageableRequestSchema.extend({
    name: z.string().optional(),
    location: z.string().optional(),
});

export type ListImmunizationsQuerySchema = z.infer<typeof listImmunizationsQuerySchema>;

export const listImmunizationsResponseSchema = createListResponseSchema(immunizationSchema);

export type ListImmunizationsResponseSchema = z.infer<typeof listImmunizationsResponseSchema>;

export const listImmunizationsErrorResponseSchema = z.discriminatedUnion("type", [
    unauthorizedResponseSchema,
]);

export type ListImmunizationsErrorResponseSchema = z.infer<typeof listImmunizationsErrorResponseSchema>;

export async function listImmunizationsApi(query: ListImmunizationsQuerySchema) {
    const response = await apiClient.get("/immunizations", query);
    return listImmunizationsResponseSchema.parse(response.data);
}

export function createListImmunizationsQueryOptions(
    query: ListImmunizationsQuerySchema,
) {
    const queryKey = ["immunizations", query] as const;
    return queryOptions<
        ListImmunizationsResponseSchema,
        ListImmunizationsErrorResponseSchema,
        ListImmunizationsQuerySchema,
        typeof queryKey
    >({
        queryKey,
        queryFn: () => listImmunizationsApi(listImmunizationsQuerySchema.parse(query)),
        throwOnError: isAxiosError,
    });
}
