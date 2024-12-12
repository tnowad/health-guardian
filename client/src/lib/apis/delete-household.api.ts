import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteHouseholdParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteHouseholdParamsSchema = z.infer<
  typeof deleteHouseholdParamsSchema
>;

export const deleteHouseholdResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteHouseholdResponseSchema = z.infer<
  typeof deleteHouseholdResponseSchema
>;

export async function deleteHouseholdApi(
  params: DeleteHouseholdParamsSchema,
): Promise<DeleteHouseholdResponseSchema> {
  const response = await apiClient.delete<DeleteHouseholdResponseSchema>(
    `/households/${params.id}`,
  );
  return deleteHouseholdResponseSchema.parse(response.data);
}

export function useDeleteHouseholdMutation(
  params: DeleteHouseholdParamsSchema,
) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-household", params] as const;

  return useMutation<DeleteHouseholdResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteHouseholdApi(deleteHouseholdParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["households"] });
      queryClient.invalidateQueries({ queryKey: ["household-members"] });
    },
    throwOnError: isAxiosError,
  });
}
