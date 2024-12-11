import { z } from "zod";
export const householdMemberSchema = z.object({
  id: z.string().uuid(),
  householdId: z.string().uuid(),
  userId: z.string().uuid(),
});
export type HouseholdMemberSchema = z.infer<typeof householdMemberSchema>;
