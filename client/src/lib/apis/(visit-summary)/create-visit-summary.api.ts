import { z } from "zod";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { getQueryClient } from "@/app/get-query-client";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";
import { visitSummarySchema } from "@/lib/schemas/(visit-summary)/visit-summary.schema";

export const createVisitSummaryBodySchema = visitSummarySchema.omit({
  id: true,
});

export type CreateVisitSummaryBodySchema = z.infer<
  typeof createVisitSummaryBodySchema
>;

export const createVisitSummaryResponseSchema = visitSummarySchema;
export type CreateVisitSummaryResponseSchema = z.infer<
  typeof createVisitSummaryResponseSchema
>;

export const createVisitSummaryErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type CreateVisitSummaryErrorResponseSchema = z.infer<
  typeof createVisitSummaryErrorResponseSchema
>;

export async function createVisitSummaryApi(
  body: CreateVisitSummaryBodySchema
): Promise<CreateVisitSummaryResponseSchema> {
  const response = await apiClient.post<CreateVisitSummaryResponseSchema>(
    `/visit-summaries`,
    body
  );
  return createVisitSummaryResponseSchema.parse(response.data);
}

export function useCreateAppointmentMutation() {
  const mutationKey = ["create-appointment"] as const;
  const queryClient = getQueryClient();
  return useMutation<
    CreateVisitSummaryResponseSchema,
    CreateVisitSummaryErrorResponseSchema,
    CreateVisitSummaryBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createVisitSummaryApi(createVisitSummaryBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ["appointments"],
      });
    },
    throwOnError: isAxiosError,
  });
}
