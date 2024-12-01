import { z } from "zod";
import { userSchema } from "../schemas/user.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listUsersQuerySchema = pageableRequestSchema.extend({
  ids: z.array(z.string().uuid()).optional(),
  type: z.string().optional(),
});
export type ListUsersQuerySchema = z.infer<typeof listUsersQuerySchema>;

export const listUsersResponseSchema = createListResponseSchema(userSchema);
export type ListUsersResponseSchema = z.infer<typeof listUsersResponseSchema>;

export const listUsersErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListUsersErrorResponseSchema = z.infer<
  typeof listUsersErrorResponseSchema
>;

export async function listUsersApi(query: ListUsersQuerySchema) {
  const response = await apiClient.get("/users", { params: query });
  return listUsersResponseSchema.parse(response.data);
}

export function createListUsersQueryOptions(query: ListUsersQuerySchema) {
  const queryKey = ["users", query] as const;
  return queryOptions<
    ListUsersResponseSchema,
    ListUsersErrorResponseSchema,
    ListUsersQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () => listUsersApi(listUsersQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
