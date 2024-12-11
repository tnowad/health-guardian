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
    name: z.string().optional(),
    location: z.string().optional(),
});

export type ListAllergiesQuerySchema = z.infer<typeof listAllergiesQuerySchema>;

export const listAllergiesResponseSchema = createListResponseSchema(allergySchema);

export type ListAllergiesResponseSchema = z.infer<typeof listAllergiesResponseSchema>;

export const listAllergiesErrorResponseSchema = z.discriminatedUnion("type", [
    unauthorizedResponseSchema,
]);

export type ListAllergiesErrorResponseSchema = z.infer<typeof listAllergiesErrorResponseSchema>;

export async function listAllergiesApi(query: ListAllergiesQuerySchema) {
    const response = await apiClient.get("/allergies", query);
    return listAllergiesResponseSchema.parse(response.data);
}

export function createListAllergiesQueryOptions(
    query: ListAllergiesQuerySchema,
) {
    const queryKey = ["allergies", query] as const;
    return queryOptions<
        ListAllergiesResponseSchema,
        ListAllergiesErrorResponseSchema,
        ListAllergiesQuerySchema,
        typeof queryKey
    >({
        queryKey,
        queryFn: () => listAllergiesApi(listAllergiesQuerySchema.parse(query)),
        throwOnError: isAxiosError,
    });
}
export async function listAllergiesByUserIdApi(userId: string, query: ListAllergiesQuerySchema) {
    const response = await apiClient.get(`/allergies/user/${userId}`, query);
    return listAllergiesResponseSchema.parse(response.data);
}

export function createListAllergiesByUserIdQueryOptions(
    userId: string,
    query: ListAllergiesQuerySchema,
) {
    const queryKey = ["userAllergies", userId, query] as const;
    return queryOptions<
        ListAllergiesResponseSchema,
        ListAllergiesErrorResponseSchema,
        ListAllergiesQuerySchema,
        typeof queryKey
    >({
        queryKey,
        queryFn: () => listAllergiesByUserIdApi(userId, listAllergiesQuerySchema.parse(query)),
        throwOnError: isAxiosError,
    });
}