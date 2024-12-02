import { z } from "zod";

export const userTypeSchema = z.enum(["PARENT", "STAFF", "MEDICAL_STAFF"]);

export const userSchema = z.object({
  id: z.string().uuid(),
  accountId: z.string().uuid(),
  userType: userTypeSchema,
});

export type UserSchema = z.infer<typeof userSchema>;
