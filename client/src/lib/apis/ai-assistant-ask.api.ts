import { z } from "zod";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { apiClient } from "../client";
import { getQueryClient } from "@/app/get-query-client";

export const aiAssistantAskBodySchema = z.object({
  question: z.string().min(1, "Question cannot be empty."),
});

export type AiAssistantAskBodySchema = z.infer<typeof aiAssistantAskBodySchema>;

// Schema for the ask question response
export const aiAssistantAskResponseSchema = z.object({
  answer: z.string(),
});

export type AiAssistantAskResponseSchema = z.infer<
  typeof aiAssistantAskResponseSchema
>;

export async function askQuestionApi(
  body: AiAssistantAskBodySchema,
): Promise<AiAssistantAskResponseSchema> {
  const response = await apiClient.post<AiAssistantAskResponseSchema>(
    "/ai-assistant/ask",
    body,
  );
  return aiAssistantAskResponseSchema.parse(response.data);
}

export function useAskQuestionMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["ask-question"] as const;

  return useMutation<
    AiAssistantAskResponseSchema,
    unknown,
    AiAssistantAskBodySchema
  >({
    mutationKey,
    mutationFn: (body) => askQuestionApi(aiAssistantAskBodySchema.parse(body)),
    onSuccess() {
      // Optionally invalidate any related queries or perform other actions
    },
    throwOnError: isAxiosError,
  });
}
