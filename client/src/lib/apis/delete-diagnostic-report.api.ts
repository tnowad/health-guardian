import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

// Schema for deleting a diagnostic report
export const deleteDiagnosticReportParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteDiagnosticReportParamsSchema = z.infer<
  typeof deleteDiagnosticReportParamsSchema
>;

// Schema for the response after deleting a diagnostic report
export const deleteDiagnosticReportResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteDiagnosticReportResponseSchema = z.infer<
  typeof deleteDiagnosticReportResponseSchema
>;

// API call to delete a diagnostic report
export async function deleteDiagnosticReportApi(
  params: DeleteDiagnosticReportParamsSchema
): Promise<DeleteDiagnosticReportResponseSchema> {
  const response = await apiClient.delete<DeleteDiagnosticReportResponseSchema>(
    `/diagnostic-reports/${params.id}`
  );
  return deleteDiagnosticReportResponseSchema.parse(response.data);
}

// Mutation hook for deleting a diagnostic report
export function useDeleteDiagnosticReportMutation(params: DeleteDiagnosticReportParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-diagnostic-report", params] as const;

  return useMutation<DeleteDiagnosticReportResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteDiagnosticReportApi(deleteDiagnosticReportParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["diagnostic-reports"] });
    },
    throwOnError: isAxiosError,
  });
}