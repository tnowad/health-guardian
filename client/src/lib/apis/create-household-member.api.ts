import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const createHouseholdMemberBodySchema = z.object({
  householdId: z.string().uuid(),
  patientId: z.string().uuid(),
  relationshipToPatient: z.string(),
});

export type CreateHouseholdMemberBodySchema = z.infer<
  typeof createHouseholdMemberBodySchema
>;

export const createHouseholdMemberResponseSchema = z.object({
  id: z.string().uuid(),
  householdId: z.string().uuid(),
  patientId: z.string().uuid(),
  relationshipToPatient: z.string(),
});

export type CreateHouseholdMemberResponseSchema = z.infer<
  typeof createHouseholdMemberResponseSchema
>;

export async function createHouseholdMemberApi(
  body: CreateHouseholdMemberBodySchema,
): Promise<CreateHouseholdMemberResponseSchema> {
  const response = await apiClient.post<CreateHouseholdMemberResponseSchema>(
    `/household-members`,
    body,
  );
  return createHouseholdMemberResponseSchema.parse(response.data);
}

export function useCreateHouseholdMemberMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-household-member"] as const;

  return useMutation<
    CreateHouseholdMemberResponseSchema,
    unknown,
    CreateHouseholdMemberBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createHouseholdMemberApi(createHouseholdMemberBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["household-members"] });
    },
    throwOnError: isAxiosError,
  });
}
