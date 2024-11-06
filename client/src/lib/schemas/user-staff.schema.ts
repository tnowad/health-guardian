import { z } from "zod";
export const userStaffSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  staffType: z.string(),
  hospitalId: z.string().uuid(),
  role: z.string(),
});
export type UserStaffSchema = z.infer<typeof userStaffSchema>;
