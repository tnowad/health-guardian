import { z } from "zod";
export const patientLogSchema = z.object({
  id: z.string().uuid(),
  patientId: z.string().uuid(),
  logType: z.string(),
  message: z.string(),
  createdAt: z.string(),
});
export type PatientLogSchema = z.infer<typeof patientLogSchema>;
