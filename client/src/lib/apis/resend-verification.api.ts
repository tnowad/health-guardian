import { z } from "zod";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { apiClient } from "../client";

export const resendVerificationBodySchema = z.object({
  email: z.string().email("Invalid email address."),
});

export type ResendVerificationBodySchema = z.infer<
  typeof resendVerificationBodySchema
>;

export const resendVerificationResponseSchema = z.object({
  message: z.string(),
});

export type ResendVerificationResponseSchema = z.infer<
  typeof resendVerificationResponseSchema
>;

export async function resendVerificationApi(
  body: ResendVerificationBodySchema,
): Promise<ResendVerificationResponseSchema> {
  const response = await apiClient.post<ResendVerificationResponseSchema>(
    "/resend-verification",
    body,
  );
  return resendVerificationResponseSchema.parse(response.data);
}

export function useResendVerificationMutation() {
  const mutationKey = ["resend-verification"] as const;

  return useMutation<
    ResendVerificationResponseSchema,
    unknown,
    ResendVerificationBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      resendVerificationApi(resendVerificationBodySchema.parse(body)),
    throwOnError: isAxiosError,
    onSuccess() {},
  });
}
