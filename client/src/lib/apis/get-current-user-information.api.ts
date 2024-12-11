import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { queryOptions } from "@tanstack/react-query";

export const currentUserResponseSchema = z.object({
  userId: z.string(),
  name: z.string(),
  email: z.string(),
  avatarUrl: z.string().nullable().optional(),
});
export type CurrentUserResponseSchema = z.infer<
  typeof currentUserResponseSchema
>;

export const currentUserErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type CurrentUserErrorResponseSchema = z.infer<
  typeof currentUserErrorResponseSchema
>;

export async function getCurrentUserInformationApi() {
  const response = await apiClient.get<CurrentUserResponseSchema>(
    "/users/current-user/information",
  );
  return response.data;
}

export function createGetCurrentUserInformationQueryOptions() {
  return queryOptions({
    queryKey: ["users", "current-user", "information"],
    queryFn: () => getCurrentUserInformationApi(),
  });
}
