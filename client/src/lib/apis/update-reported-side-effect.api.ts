import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { reportedSideEffectSchema } from "../schemas/reported-side-effect.schema";

export const updateReportedSideEffectBodySchema = reportedSideEffectSchema;

export type UpdateReportedSideEffectBodySchema = z.infer<
  typeof updateReportedSideEffectBodySchema
>;

export const updateReportedSideEffectResponseSchema = reportedSideEffectSchema;

export type UpdateReportedSideEffectResponseSchema = z.infer<
  typeof updateReportedSideEffectResponseSchema
>;

export async function updateReportedSideEffectApi(
  body: UpdateReportedSideEffectBodySchema,
): Promise<UpdateReportedSideEffectResponseSchema> {
  const response = await apiClient.put<UpdateReportedSideEffectResponseSchema>(
    `/reported-side-effects/${body.id}`,
    body,
  );
  return updateReportedSideEffectResponseSchema.parse(response.data);
}

export function useUpdateReportedSideEffectMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-reported-side-effect"] as const;

  return useMutation<
    UpdateReportedSideEffectResponseSchema,
    unknown,
    UpdateReportedSideEffectBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateReportedSideEffectApi(
        updateReportedSideEffectBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["reported-side-effects"] });
    },
    throwOnError: isAxiosError,
  });
}
