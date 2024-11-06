import { z } from "zod";
import { apiClient } from "../client";
import { tokenSchema } from "../schemas/token.schema";
import { useMutation } from "@tanstack/react-query";
import { setCookie } from "cookies-next";
import { COOKIE_KEY_ACCESS_TOKEN, COOKIE_KEY_REFRESH_TOKEN } from "@/constants";

export const signInBodySchema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});
export type SignInBodySchema = z.infer<typeof signInBodySchema>;
export const signInResponseSchema = z.object({
  message: z.string(),
  tokens: tokenSchema,
});
export type SignInResponseSchema = z.infer<typeof signInResponseSchema>;
export const signInErrorResponseSchema = z.object({
  message: z.string(),
});
export type SignInErrorResponseSchema = z.infer<
  typeof signInErrorResponseSchema
>;

export async function signInApi(
  body: SignInBodySchema,
): Promise<SignInResponseSchema> {
  const response = await apiClient.post<SignInResponseSchema>(
    "/auth/sign-in",
    body,
    {
      headers: {
        "No-Auth": true,
      },
    },
  );
  return response.data;
}

export function useSignInMutation() {
  return useMutation({
    mutationKey: ["sign-in"],
    mutationFn: (body: SignInBodySchema) => signInApi(body),
    onSuccess: (data) => {
      setCookie(COOKIE_KEY_ACCESS_TOKEN, data.tokens.accessToken);
      setCookie(COOKIE_KEY_REFRESH_TOKEN, data.tokens.refreshToken);
    },
  });
}
