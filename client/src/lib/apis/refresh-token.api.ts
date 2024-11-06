import { z } from "zod";
import {
  accessTokenSchema,
  refreshTokenSchema,
  tokenSchema,
} from "../schemas/token.schema";
import { apiClient } from "../client";

export const refreshTokenBodySchema = z.object({
  refreshToken: refreshTokenSchema,
});
export type RefreshTokenBodySchema = z.infer<typeof refreshTokenBodySchema>;

export const refreshTokenResponseSchema = z.object({
  message: z.string(),
  tokens: tokenSchema,
});
export type RefreshTokenResponseSchema = z.infer<
  typeof refreshTokenResponseSchema
>;

export const refreshTokenErrorResponseSchema = z.object({
  accessToken: accessTokenSchema,
});
export type RefreshTokenErrorResponseSchema = z.infer<
  typeof refreshTokenErrorResponseSchema
>;

export async function refreshTokenApi(
  body: RefreshTokenBodySchema,
): Promise<RefreshTokenResponseSchema> {
  const response = await apiClient.post<RefreshTokenResponseSchema>(
    "/auth/refresh",
    body,
    {
      headers: {
        "No-Auth": true,
      },
    },
  );
  return response.data;
}
