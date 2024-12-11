import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { householdSchema } from "../schemas/household.schema";

export const updateHouseholdBodySchema = householdSchema;

export type UpdateHouseholdBodySchema = z.infer<
  typeof updateHouseholdBodySchema
>;

export const updateHouseholdResponseSchema = z.object({
  id: z.string().uuid(),
  head: z.string().uuid(),
});

export type UpdateHouseholdResponseSchema = z.infer<
  typeof updateHouseholdResponseSchema
>;

export async function updateHouseholdApi(
  body: UpdateHouseholdBodySchema,
): Promise<UpdateHouseholdResponseSchema> {
  const response = await apiClient.put<UpdateHouseholdResponseSchema>(
    `/households/${body.id}`,
    body,
  );
  return updateHouseholdResponseSchema.parse(response.data);
}

export function useUpdateHouseholdMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-household"] as const;

  return useMutation<
    UpdateHouseholdResponseSchema,
    unknown,
    UpdateHouseholdBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateHouseholdApi(updateHouseholdBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["households"] });
    },
    throwOnError: isAxiosError,
  });
}
