import { z } from "zod";
export const householdSchema = z.object({
  id: z.string().uuid(),
  headId: z.string().uuid(),
  avatar: z.string().optional(),
  name: z.string(),
});
export type HouseholdSchema = z.infer<typeof householdSchema>;
