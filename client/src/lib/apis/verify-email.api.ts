import { z } from "zod";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { apiClient } from "../client";
import { getQueryClient } from "@/app/get-query-client";

export const verifyEmailBodySchema = z.object({
  email: z.string().email("Invalid email address."),
  code: z.string().min(6, "Verification code must be at least 6 characters."),
});

export type VerifyEmailBodySchema = z.infer<typeof verifyEmailBodySchema>;

export const verifyEmailResponseSchema = z.object({
  message: z.string(),
});

export type VerifyEmailResponseSchema = z.infer<
  typeof verifyEmailResponseSchema
>;

export async function verifyEmailApi(
  body: VerifyEmailBodySchema,
): Promise<VerifyEmailResponseSchema> {
  const response = await apiClient.post<VerifyEmailResponseSchema>(
    "/auth/verify-email-by-code",
    body,
  );
  return response.data;
}

export function useVerifyEmailMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["verify-email"] as const;

  return useMutation<VerifyEmailResponseSchema, unknown, VerifyEmailBodySchema>(
    {
      mutationKey,
      mutationFn: (body) => verifyEmailApi(verifyEmailBodySchema.parse(body)),
      onSuccess() {
        queryClient.invalidateQueries({ queryKey: ["user-profile"] });
      },
      throwOnError: isAxiosError,
    },
  );
}
