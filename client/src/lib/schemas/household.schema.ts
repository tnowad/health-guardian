import { z } from "zod";
export const householdSchema = z.object({
  id: z.string().uuid(),
  head: z.string().uuid(),
});
export type HouseholdSchema = z.infer<typeof householdSchema>;
