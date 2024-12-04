import { z } from "zod";
import { userStaffSchema } from "../schemas/user-staff.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listUserStaffsQuerySchema = pageableRequestSchema.extend({
  role: z.string().optional(),
  roleType: z.string().optional(),
});
export type ListUserStaffsQuerySchema = z.infer<
  typeof listUserStaffsQuerySchema
>;

export const listUserStaffsResponseSchema =
  createListResponseSchema(userStaffSchema);
export type ListUserStaffsResponseSchema = z.infer<
  typeof listUserStaffsResponseSchema
>;

export const listUserStaffsErrorResponseSchema = z.discriminatedUnion("type", [
  unauthorizedResponseSchema,
]);
export type ListUserStaffsErrorResponseSchema = z.infer<
  typeof listUserStaffsErrorResponseSchema
>;

export async function listUserStaffsApi(query: ListUserStaffsQuerySchema) {
  const response = await apiClient.get("/user-staffs", query );
  return listUserStaffsResponseSchema.parse(response.data);
}

export function createListUserStaffsQueryOptions(
  query: ListUserStaffsQuerySchema,
) {
  const queryKey = ["user-staffs", query] as const;
  return queryOptions<
    ListUserStaffsResponseSchema,
    ListUserStaffsErrorResponseSchema,
    ListUserStaffsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () => listUserStaffsApi(listUserStaffsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
