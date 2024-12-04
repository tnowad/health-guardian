import { z } from "zod";
import { userMedicalStaffSchema } from "../schemas/user-medical-staff.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import {
  InfiniteData,
  infiniteQueryOptions,
  queryOptions,
} from "@tanstack/react-query";
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
  const response = await apiClient.get("/user-medical-staffs", query);
  return listUserMedicalStaffsResponseSchema.parse(response.data);
}

export function createListUserMedicalStaffsQueryOptions(
  query: ListUserMedicalStaffsQuerySchema,
) {
  const queryKey = ["user-medical-staffs", query] as const;
  return queryOptions<ListUserMedicalStaffsResponseSchema>({
    queryKey,
    queryFn: () =>
      listUserMedicalStaffsApi(listUserMedicalStaffsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}

export function createListUserMedicalStaffsInfinityQueryOptions(
  query: ListUserMedicalStaffsQuerySchema,
) {
  const queryKey = ["user-medical-staffs", "infinity", query] as const;
  return infiniteQueryOptions<
    ListUserMedicalStaffsResponseSchema,
    ListUserMedicalStaffsErrorResponseSchema,
    InfiniteData<ListUserMedicalStaffsResponseSchema>,
    typeof queryKey,
    ListUserMedicalStaffsQuerySchema
  >({
    queryKey,
    queryFn: ({ pageParam }) =>
      listUserMedicalStaffsApi(
        listUserMedicalStaffsQuerySchema.parse({
          ...query,
          ...pageParam,
        }),
      ),
    getNextPageParam: (lastPage) =>
      lastPage.pageable.pageNumber + 1 < lastPage.totalPages
        ? {
            page: lastPage.pageable.pageNumber + 1,
            size: lastPage.pageable.pageSize,
          }
        : undefined,
    initialPageParam: { page: 0, size: 20 },
  });
}
