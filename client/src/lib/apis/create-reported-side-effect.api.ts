import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { reportedSideEffectSchema } from "../schemas/reported-side-effect.schema";

export const createReportedSideEffectBodySchema = reportedSideEffectSchema.omit(
  {
    id: true, // Omit the ID as it's generated on the backend
  },
);

export type CreateReportedSideEffectBodySchema = z.infer<
  typeof createReportedSideEffectBodySchema
>;

export const createReportedSideEffectResponseSchema = reportedSideEffectSchema;

export type CreateReportedSideEffectResponseSchema = z.infer<
  typeof createReportedSideEffectResponseSchema
>;

export async function createReportedSideEffectApi(
  body: CreateReportedSideEffectBodySchema,
): Promise<CreateReportedSideEffectResponseSchema> {
  const response = await apiClient.post<CreateReportedSideEffectResponseSchema>(
    `/reported-side-effects`,
    body,
  );
  return createReportedSideEffectResponseSchema.parse(response.data);
}

export function useCreateReportedSideEffectMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-reported-side-effect"] as const;

  return useMutation<
    CreateReportedSideEffectResponseSchema,
    unknown,
    CreateReportedSideEffectBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createReportedSideEffectApi(
        createReportedSideEffectBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["reported-side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
