import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { surgerySchema } from "../schemas/surgery.schema";

export const createSurgeryBodySchema = surgerySchema.omit({
  id: true,
});

export type CreateSurgeryBodySchema = z.infer<
  typeof createSurgeryBodySchema
>;

export const createSurgeryResponseSchema = surgerySchema;

export type CreateSurgeryResponseSchema = z.infer<
  typeof createSurgeryResponseSchema
>;

export async function createSurgeryApi(
  body: CreateSurgeryBodySchema,
): Promise<CreateSurgeryResponseSchema> {
  const response =
    await apiClient.post<CreateSurgeryResponseSchema>(
      `/surgeries`,
      body,
    );
  return createSurgeryResponseSchema.parse(response.data);
}

export function useCreateSurgeryMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-surgery"] as const;

  return useMutation<
    CreateSurgeryResponseSchema,
    unknown,
    CreateSurgeryBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createSurgeryApi(
        createSurgeryBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["surgeries"] });
    },
    throwOnError: isAxiosError,
  });
}


