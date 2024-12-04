import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import {
    questionRequestSchema,
    questionResponseSchema,
} from "../schemas/ai-assistant.schema";

export type QuestionRequest = z.infer<typeof questionRequestSchema>;
export type QuestionResponse = z.infer<typeof questionResponseSchema>;


export async function askQuestionApi(
    body: QuestionRequest,
): Promise<QuestionResponse> {
    const response = await apiClient.post<QuestionResponse>(
        "/ai-assistant/ask",
        body,
    );
    return questionResponseSchema.parse(response.data);
}

export function useAskQuestionMutation() {
    const queryClient = getQueryClient();
    const mutationKey = ["ask-question"] as const;

    return useMutation<QuestionResponse, unknown, QuestionRequest>({
        mutationKey,
        mutationFn: (body) =>
            askQuestionApi(questionRequestSchema.parse(body)),
        onSuccess() {
            queryClient.invalidateQueries({ queryKey: ["ai-assistant"] });
        }
    });
}



