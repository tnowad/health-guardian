import { z } from "zod";
export const externalProviderSchema = z.object({
  id: z.string().uuid(),
  accountId: z.string().uuid(),
  providerName: z.string(),
  providerUserId: z.string(),
  token: z.string(),
});
export type ExternalProviderSchema = z.infer<typeof externalProviderSchema>;
