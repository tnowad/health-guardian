import { z } from "zod";
import { surgerySchema } from "../schemas/surgery.schema";
import {
    createListResponseSchema,
    pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listSurgeriesQuerySchema = pageableRequestSchema.extend({
    name: z.string().optional(),
    location: z.string().optional(),
});

export type ListSurgeriesQuerySchema = z.infer<typeof listSurgeriesQuerySchema>;

export const listSurgeriesResponseSchema = createListResponseSchema(surgerySchema);

export type ListSurgeriesResponseSchema = z.infer<typeof listSurgeriesResponseSchema>;

export const listSurgeriesErrorResponseSchema = z.discriminatedUnion("type", [
    unauthorizedResponseSchema,
]);

export type ListSurgeriesErrorResponseSchema = z.infer<typeof listSurgeriesErrorResponseSchema>;

export async function listSurgeriesApi(query: ListSurgeriesQuerySchema) {
    const response = await apiClient.get("/surgeries", query);
    return listSurgeriesResponseSchema.parse(response.data);
}

export function createListSurgeriesQueryOptions(
    query: ListSurgeriesQuerySchema,
) {
    const queryKey = ["surgeries", query] as const;
    return queryOptions<
        ListSurgeriesResponseSchema,
        ListSurgeriesErrorResponseSchema,
        ListSurgeriesQuerySchema,
        typeof queryKey
    >({
        queryKey,
        queryFn: () => listSurgeriesApi(listSurgeriesQuerySchema.parse(query)),
        throwOnError: isAxiosError,
    });
}

