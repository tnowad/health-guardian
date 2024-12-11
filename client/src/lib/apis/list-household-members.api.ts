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

export const listHouseholdMembersQuerySchema = pageableRequestSchema.extend({
  relationshipToPatient: z.string().optional(),
  memberId: z.string().uuid().optional(),
  householdId: z.string().uuid().optional(),
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
  query: ListHouseholdMembersQuerySchema,
) {
  const response = await apiClient.get<ListHouseholdMembersResponseSchema>(
    `/household-members`,
    query,
  );
  return response.data;
}

export function createListHouseholdMembersQueryOptions(
  query: ListHouseholdMembersQuerySchema,
) {
  const queryKey = ["household-members", query] as const;
  return queryOptions<ListHouseholdMembersResponseSchema, typeof queryKey>({
    queryKey,
    queryFn: () =>
      listHouseholdMembersApi(listHouseholdMembersQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
