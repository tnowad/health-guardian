import { z } from "zod";

export const userTypeSchema = z.enum(["PARENT", "STAFF", "MEDICAL_STAFF"]);

export const userSchema = z.object({
  id: z.string().uuid(),
  email: z.string().email(),
  avatar: z.string().nullable().optional(),
  firstName: z.string().min(1).max(255),
  lastName: z.string().min(1).max(255),
  dob: z.string().regex(/^\d{4}-\d{2}-\d{2}$/),
  gender: z.enum(["MALE", "FEMALE", "OTHER"]),
  address: z.string().min(1).max(255),
});

export type UserSchema = z.infer<typeof userSchema>;
