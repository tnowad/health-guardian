import { z } from "zod";
export const familyHistorySchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  relation: z.string(),
  condition: z.string(),
  description: z.string(),
});
export type FamilyHistorySchema = z.infer<typeof familyHistorySchema>;
