import { z } from "zod";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";

export const getCurrentUserPermissionsResponseSchema = z.object({
  items: z.array(z.string()),
});

export type GetCurrentUserPermissionsResponseSchema = z.infer<
  typeof getCurrentUserPermissionsResponseSchema
>;

export async function getCurrentUserPermissionsApi() {
  const response = await apiClient.get<GetCurrentUserPermissionsResponseSchema>(
    "/auth/current-user/permissions",
  );
  return response.data;
}

export function createGetCurrentUserPermissionsQueryOptions() {
  const queryKey = ["current-user-permissions"];
  return queryOptions<GetCurrentUserPermissionsResponseSchema>({
    queryKey,
    queryFn: () => getCurrentUserPermissionsApi(),
  });
}
