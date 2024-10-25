import { z } from "zod";
import { apiClient } from "./client";

export const unknownErrorSchema = z.object({
  type: z.literal("UnknownError"),
  message: z.string(),
});

export const networkErrorSchema = z.object({
  type: z.literal("NetworkError"),
  message: z.string(),
});

export const validationErrorResponseSchema = z.object({
  type: z.literal("ValidationError"),
  message: z.string(),
  errors: z.record(z.array(z.string())),
});

export const tokensResponseSchema = z.object({
  accessToken: z.string(),
  refreshToken: z.string(),
});
export type TokensResponse = z.infer<typeof tokensResponseSchema>;

export const profileResponseSchema = z.object({
  id: z.string(),
  email: z.string(),
  name: z.string(),
  avatar: z.string().nullable(),
  type: z.enum(["PATIENT", "STAFF"]),
});

export const signInRequestSchema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});
export type SignInRequest = z.infer<typeof signInRequestSchema>;

export const signInResponseSchema = z.object({
  message: z.string(),
  tokens: tokensResponseSchema,
  profile: profileResponseSchema,
});
export type SignInResponse = z.infer<typeof signInResponseSchema>;

export const signInErrorResponseSchema = z.discriminatedUnion("type", [
  validationErrorResponseSchema,
  unknownErrorSchema,
  networkErrorSchema,
]);
export type SignInErrorResponse = z.infer<typeof signInErrorResponseSchema>;

export async function signInApi(data: SignInRequest): Promise<SignInResponse> {
  const response = await apiClient.post<SignInResponse, SignInRequest>(
    "/auth/sign-in",
    data,
  );

  return response.data;
}
