import { z } from "zod";
export const userStaffSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  firstName: z.string(),
  lastName: z.string(),
  staffType: z.string(),
  hospitalId: z.string().uuid(),
  role: z.string(),
});
export type UserStaffSchema = z.infer<typeof userStaffSchema>;
