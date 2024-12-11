import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { diagnosticResultSchema } from "../schemas/diagnostic-result.schema";
import {encodePng} from "next/dist/server/lib/squoosh/impl";

export const createDiagnosticResultBodySchema = diagnosticResultSchema.omit({
  id: true,
});

export type CreateDiagnosticResultBodySchema = z.infer<
  typeof createDiagnosticResultBodySchema
>;

export const createDiagnosticResultResponseSchema = diagnosticResultSchema;


export type CreateDiagnosticResultResponseSchema = z.infer<
  typeof createDiagnosticResultResponseSchema
>;

export async function createDiagnosticResultApi(
  body: CreateDiagnosticResultBodySchema,
): Promise<CreateDiagnosticResultResponseSchema> {
  const response =
    await apiClient.post<CreateDiagnosticResultResponseSchema>(
      `/diagnostic-results`,
      body,
    );
  return createDiagnosticResultResponseSchema.parse(response.data);
}

export function useCreateDiagnosticResultMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-diagnostic-result"] as const;

  return useMutation<
    CreateDiagnosticResultResponseSchema,
    unknown,
    CreateDiagnosticResultBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createDiagnosticResultApi(
        createDiagnosticResultBodySchema.parse(body),
      ),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["diagnostic-results"] });
    },
    throwOnError: isAxiosError,
  });
}
