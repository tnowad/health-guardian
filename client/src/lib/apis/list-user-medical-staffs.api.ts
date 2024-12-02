import { z } from "zod";
import { userMedicalStaffSchema } from "../schemas/user-medical-staff.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listUserMedicalStaffsQuerySchema = pageableRequestSchema.extend({
  hospitalId: z.string().uuid().optional(),
  staffType: z.string().optional(),
  specialization: z.string().optional(),
  role: z.string().optional(),
  active: z.boolean().optional(),
});
export type ListUserMedicalStaffsQuerySchema = z.infer<
  typeof listUserMedicalStaffsQuerySchema
>;

export const listUserMedicalStaffsResponseSchema = createListResponseSchema(
  userMedicalStaffSchema,
);
export type ListUserMedicalStaffsResponseSchema = z.infer<
  typeof listUserMedicalStaffsResponseSchema
>;

export const listUserMedicalStaffsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListUserMedicalStaffsErrorResponseSchema = z.infer<
  typeof listUserMedicalStaffsErrorResponseSchema
>;

export async function listUserMedicalStaffsApi(
  query: ListUserMedicalStaffsQuerySchema,
) {
  const response = await apiClient.get("/user-medical-staffs", {
    params: query,
  });
  return listUserMedicalStaffsResponseSchema.parse(response.data);
}

export function createListUserMedicalStaffsQueryOptions(
  query: ListUserMedicalStaffsQuerySchema,
) {
  const queryKey = ["user-medical-staffs", query] as const;
  return queryOptions<
    ListUserMedicalStaffsResponseSchema,
    ListUserMedicalStaffsErrorResponseSchema,
    ListUserMedicalStaffsQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listUserMedicalStaffsApi(listUserMedicalStaffsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
