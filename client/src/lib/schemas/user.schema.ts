import { z } from "zod";
export const userSchema = z.object({
  id: z.string().uuid(),
  accountId: z.string().uuid(),
  userType: z.string(),
});
export type UserSchema = z.infer<typeof userSchema>;
