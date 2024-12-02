import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteHouseholdMemberParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteHouseholdMemberParamsSchema = z.infer<
  typeof deleteHouseholdMemberParamsSchema
>;

export const deleteHouseholdMemberResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteHouseholdMemberResponseSchema = z.infer<
  typeof deleteHouseholdMemberResponseSchema
>;

export async function deleteHouseholdMemberApi(
  params: DeleteHouseholdMemberParamsSchema,
): Promise<DeleteHouseholdMemberResponseSchema> {
  const response = await apiClient.delete<DeleteHouseholdMemberResponseSchema>(
    `/household-members/${params.id}`,
  );
  return deleteHouseholdMemberResponseSchema.parse(response.data);
}

export function useDeleteHouseholdMemberMutation(
  params: DeleteHouseholdMemberParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-household-member", params] as const;

  return useMutation<DeleteHouseholdMemberResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteHouseholdMemberApi(deleteHouseholdMemberParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["household-members"] });
    },
    throwOnError: isAxiosError,
  });
}
