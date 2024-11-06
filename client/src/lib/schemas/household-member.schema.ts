import { z } from "zod";
export const householdMemberSchema = z.object({
  id: z.string().uuid(),
  householdId: z.string().uuid(),
  patientId: z.string().uuid(),
  relationshipToPatient: z.string(),
});
export type HouseholdMemberSchema = z.infer<typeof householdMemberSchema>;
