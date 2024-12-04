import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { queryOptions } from "@tanstack/react-query";

export const currentUserResponseSchema = z.object({
  userId: z.string(),
  role: z.enum(["STAFF", "MEDICAL_STAFF", "ADMIN"]),
  name: z.string(),
  username: z.string(),
  email: z.string(),
  staff: z
    .object({
      id: z.string(),
    })
    .nullable()
    .optional(),
  medicalStaff: z
    .object({
      id: z.string(),
      specialization: z.string(),
    })
    .nullable()
    .optional(),
  patient: z
    .object({
      id: z.string(),
    })
    .nullable()
    .optional(),
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
  const response = await apiClient.get("/users/current-user/information");
  return currentUserResponseSchema.parse(response.data);
}

export function createGetCurrentUserInformationQueryOptions() {
  return queryOptions({
    queryKey: ["users", "current-user", "information"],
    queryFn: () => getCurrentUserInformationApi(),
  });
}
