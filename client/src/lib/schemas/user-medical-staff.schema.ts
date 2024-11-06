import { z } from "zod";
export const userMedicalStaffSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  hospitalId: z.string().uuid(),
  staffType: z.string(),
  specialization: z.string(),
  role: z.string(),
  active: z.boolean(),
  endDate: z.string(),
});
export type UserMedicalStaffSchema = z.infer<typeof userMedicalStaffSchema>;
