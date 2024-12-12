import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { getQueryClient } from "@/app/get-query-client";

export const joinHouseholdBodySchema = z.object({
  householdId: z.string().uuid(),
  userId: z.string().uuid(),
});

export type JoinHouseholdBodySchema = z.infer<typeof joinHouseholdBodySchema>;

export const joinHouseholdResponseSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  householdId: z.string().uuid(),
});

export type JoinHouseholdResponseSchema = z.infer<
  typeof joinHouseholdResponseSchema
>;

export async function joinHouseholdApi(
  body: JoinHouseholdBodySchema,
): Promise<JoinHouseholdResponseSchema> {
  const response = await apiClient.post<JoinHouseholdResponseSchema>(
    `/households/${body.householdId}/join`,
    body,
  );
  return response.data;
}

export function useJoinHouseholdMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["join-household"] as const;

  return useMutation<
    JoinHouseholdResponseSchema,
    unknown,
    JoinHouseholdBodySchema
  >({
    mutationKey,
    mutationFn: (body) => joinHouseholdApi(joinHouseholdBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["households"] });
      queryClient.invalidateQueries({ queryKey: ["users"] });
    },
    throwOnError: isAxiosError,
  });
}
