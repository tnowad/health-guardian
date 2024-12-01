import { z } from "zod";
import { householdMemberSchema } from "../schemas/household-member.schema";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "../schemas/page.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { apiClient } from "../client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listHouseholdMemebrsParamsSchema = z.object({
  householdId: z.string().uuid().optional(),
});
export type ListHouseholdMemebrsParamsSchema = z.infer<
  typeof listHouseholdMemebrsParamsSchema
>;

export const listHouseholdMembersQuerySchema = pageableRequestSchema.extend({
  relationshipToPatient: z.string().optional(),
});
export type ListHouseholdMembersQuerySchema = z.infer<
  typeof listHouseholdMembersQuerySchema
>;

export const listHouseholdMembersResponseSchema = createListResponseSchema(
  householdMemberSchema,
);
export type ListHouseholdMembersResponseSchema = z.infer<
  typeof listHouseholdMembersResponseSchema
>;

export const listHouseholdMembersErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type ListHouseholdMembersErrorResponseSchema = z.infer<
  typeof listHouseholdMembersErrorResponseSchema
>;

export async function listHouseholdMembersApi(
  params: ListHouseholdMemebrsParamsSchema,
  query: ListHouseholdMembersQuerySchema,
) {
  const response = await apiClient.get(
    `/households/${params.householdId}/members`,
    query,
  );
  return listHouseholdMembersResponseSchema.parse(response.data);
}

export function createListHouseholdMembersQueryOptions(
  params: ListHouseholdMemebrsParamsSchema,
  query: ListHouseholdMembersQuerySchema,
) {
  const queryKey = ["household-members", params, query] as const;
  return queryOptions<
    ListHouseholdMembersResponseSchema,
    ListHouseholdMembersErrorResponseSchema,
    ListHouseholdMembersQuerySchema,
    typeof queryKey
  >({
    queryKey,
    queryFn: () =>
      listHouseholdMembersApi(
        listHouseholdMemebrsParamsSchema.parse(params),
        listHouseholdMembersQuerySchema.parse(query),
      ),
    throwOnError: isAxiosError,
  });
}
