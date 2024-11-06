import { z } from "zod";
export const localProviderSchema = z.object({
  id: z.string().uuid(),
  accountId: z.string().uuid(),
  email: z.string().email(),
  passwordHash: z.string(),
});
export type LocalProviderSchema = z.infer<typeof localProviderSchema>;
