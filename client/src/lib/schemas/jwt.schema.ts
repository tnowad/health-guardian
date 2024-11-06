import { z } from "zod";

export const tokenPayloadSchema = z.object({
  sub: z.string(),
  exp: z.number(),
  iat: z.number(),
  type: z.enum(["Access", "Refresh"]),
});
export const accessTokenPayloadSchema = tokenPayloadSchema.extend({
  profileId: z.string().uuid().optional(),
  type: z.literal("Access"),
  role: z.string().uuid(),
});
export const refreshTokenPayloadSchema = tokenPayloadSchema.extend({
  type: z.literal("Refresh"),
  jti: z.string(),
});

export const refreshTokenResponseSchema = z.string();
export const accessTokenResponseSchema = z.string();

export const tokensResponseSchema = z.object({
  accessToken: accessTokenResponseSchema,
  refreshToken: refreshTokenResponseSchema,
});
