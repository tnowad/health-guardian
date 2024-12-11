import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const createHouseholdBodySchema = z.object({
  name: z.string().min(1).max(255),
  headId: z.string().uuid(),
  avatar: z.string().nullable().optional(),
});

export type CreateHouseholdBodySchema = z.infer<
  typeof createHouseholdBodySchema
>;

export const createHouseholdResponseSchema = z.object({
  id: z.string().uuid(),
});

export type CreateHouseholdResponseSchema = z.infer<
  typeof createHouseholdResponseSchema
>;

export async function createHouseholdApi(
  body: CreateHouseholdBodySchema,
): Promise<CreateHouseholdResponseSchema> {
  const response = await apiClient.post<CreateHouseholdResponseSchema>(
    `/households`,
    body,
  );
  return createHouseholdResponseSchema.parse(response.data);
}

export function useCreateHouseholdMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-household"] as const;

  return useMutation<
    CreateHouseholdResponseSchema,
    unknown,
    CreateHouseholdBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createHouseholdApi(createHouseholdBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["households"] });
    },
    throwOnError: isAxiosError,
  });
}
