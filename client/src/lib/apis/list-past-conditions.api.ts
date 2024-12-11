import { z } from "zod";
import { pastConditionSchema } from "../schemas/past-condition.schema";
import {
    createListResponseSchema,
    pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listPastConditionsQuerySchema = pageableRequestSchema.extend({
    name: z.string().optional(),
    location: z.string().optional(),
});

export type ListPastConditionsQuerySchema = z.infer<typeof listPastConditionsQuerySchema>;

export const listPastConditionsResponseSchema = createListResponseSchema(pastConditionSchema);

export type ListPastConditionsResponseSchema = z.infer<typeof listPastConditionsResponseSchema>;

export const listPastConditionsErrorResponseSchema = z.discriminatedUnion("type", [
    unauthorizedResponseSchema,
]);

export type ListPastConditionsErrorResponseSchema = z.infer<typeof listPastConditionsErrorResponseSchema>;

export async function listPastConditionsApi(query: ListPastConditionsQuerySchema) {
    const response = await apiClient.get<ListPastConditionsResponseSchema>("/past-conditions", query);
    return response.data;
}

export function createListPastConditionsQueryOptions(
    query: ListPastConditionsQuerySchema,
) {
    const queryKey = ["past-conditions", query] as const;
    return queryOptions<
        ListPastConditionsResponseSchema,
        ListPastConditionsErrorResponseSchema,
        ListPastConditionsQuerySchema,
        typeof queryKey
    >({
        queryKey,
        queryFn: () => listPastConditionsApi(listPastConditionsQuerySchema.parse(query)),
        throwOnError: isAxiosError,
    });
}

