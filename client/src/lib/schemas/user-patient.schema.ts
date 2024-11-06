import { z } from "zod";
export const userPatientSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  patientId: z.string().uuid(),
});
export type UserPatientSchema = z.infer<typeof userPatientSchema>;
