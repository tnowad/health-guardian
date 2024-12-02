import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const updateHouseholdMemberBodySchema = z.object({
  id: z.string().uuid(),
  householdId: z.string().uuid(),
  patientId: z.string().uuid(),
  relationshipToPatient: z.string(),
});

export type UpdateHouseholdMemberBodySchema = z.infer<
  typeof updateHouseholdMemberBodySchema
>;

export const updateHouseholdMemberResponseSchema = z.object({
  id: z.string().uuid(),
  householdId: z.string().uuid(),
  patientId: z.string().uuid(),
  relationshipToPatient: z.string(),
});

export type UpdateHouseholdMemberResponseSchema = z.infer<
  typeof updateHouseholdMemberResponseSchema
>;

export async function updateHouseholdMemberApi(
  body: UpdateHouseholdMemberBodySchema,
): Promise<UpdateHouseholdMemberResponseSchema> {
  const response = await apiClient.put<UpdateHouseholdMemberResponseSchema>(
    `/household-members/${body.id}`,
    body,
  );
  return updateHouseholdMemberResponseSchema.parse(response.data);
}

export function useUpdateHouseholdMemberMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-household-member"] as const;

  return useMutation<
    UpdateHouseholdMemberResponseSchema,
    unknown,
    UpdateHouseholdMemberBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateHouseholdMemberApi(updateHouseholdMemberBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["household-members"] });
    },
    throwOnError: isAxiosError,
  });
}
