import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";

export const getHouseholdMemberDetailResponseSchema = z.object({
  id: z.string().uuid(),
  householdId: z.string().uuid(),
  patientId: z.string().uuid(),
  relationshipToPatient: z.string(),
});

export type GetHouseholdMemberDetailResponseSchema = z.infer<
  typeof getHouseholdMemberDetailResponseSchema
>;

export const getHouseholdMemberDetailErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);

export type GetHouseholdMemberDetailErrorResponseSchema = z.infer<
  typeof getHouseholdMemberDetailErrorResponseSchema
>;

export async function getHouseholdMemberDetailApi(
  id: string,
): Promise<GetHouseholdMemberDetailResponseSchema> {
  const response = await apiClient.get<GetHouseholdMemberDetailResponseSchema>(
    `/household-members/${id}`,
  );
  return getHouseholdMemberDetailResponseSchema.parse(response.data);
}

export function useGetHouseholdMemberDetailQuery(id: string) {
  const queryKey = ["household-member-detail", id] as const;
  return queryOptions<
    GetHouseholdMemberDetailResponseSchema,
    GetHouseholdMemberDetailErrorResponseSchema
  >({
    queryKey,
    enabled: !!id,
    queryFn: () => getHouseholdMemberDetailApi(id),
  });
}
