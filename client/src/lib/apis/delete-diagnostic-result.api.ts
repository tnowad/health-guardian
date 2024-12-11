import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
// Schema for deleting a diagnostic result
export const deleteDiagnosticResultParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteDiagnosticResultParamsSchema = z.infer<
  typeof deleteDiagnosticResultParamsSchema
>;

// Schema for the response after deleting a diagnostic result
export const deleteDiagnosticResultResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteDiagnosticResultResponseSchema = z.infer<
  typeof deleteDiagnosticResultResponseSchema
>;

// API call to delete a diagnostic result
export async function deleteDiagnosticResultApi(
  params: DeleteDiagnosticResultParamsSchema
): Promise<DeleteDiagnosticResultResponseSchema> {
  const response = await apiClient.delete<DeleteDiagnosticResultResponseSchema>(
    `/diagnostic-results/${params.id}`
  );
  return deleteDiagnosticResultResponseSchema.parse(response.data);
}

// Mutation hook for deleting a diagnostic result
export function useDeleteDiagnosticResultMutation(params: DeleteDiagnosticResultParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-diagnostic-result", params] as const;

  return useMutation<DeleteDiagnosticResultResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteDiagnosticResultApi(deleteDiagnosticResultParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["diagnostic-results"] });
    },
    throwOnError: isAxiosError,
  });
}
