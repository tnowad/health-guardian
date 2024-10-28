import { z } from "zod";

export const baseAccountProviderSchema = z.object({
  id: z.string(),
  accountId: z.string().uuid(),
  type: z.enum(["Google", "Facebook", "Local"]),
});
export const localAccountProviderSchema = baseAccountProviderSchema.extend({
  type: z.literal("Local"),
  email: z.string().email(),
  password: z.string().min(8),
});
export const googleAccountProviderSchema = baseAccountProviderSchema.extend({
  type: z.literal("Google"),
});
export const facebookAccountProviderSchema = baseAccountProviderSchema.extend({
  type: z.literal("Facebook"),
});

export const localAccountProviderRequestSchema =
  localAccountProviderSchema.omit({
    id: true,
    accountId: true,
  });
