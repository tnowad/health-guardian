import { z } from "zod";
export const hospitalSchema = z.object({
  id: z.string().uuid(),
  name: z.string(),
  location: z.string(),
  phone: z.string().regex(/^\+?[0-9]*$/),
  email: z.string().email().optional(),
});
export type HospitalSchema = z.infer<typeof hospitalSchema>;
