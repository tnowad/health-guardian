import { z } from "zod";
export const guardianSchema = z.object({
  id: z.string().uuid(),
  name: z.string(),
  relationshipToPatient: z.string(),
  phone: z.string().regex(/^\+?[0-9]*$/),
  email: z.string().email().optional(),
});
export type GuardianSchema = z.infer<typeof guardianSchema>;
