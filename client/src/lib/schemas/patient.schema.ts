import { z } from "zod";
export const patientSchema = z.object({
  id: z.string().uuid(),
  firstName: z.string(),
  lastName: z.string(),
  dob: z.string(),
  gender: z.string(),
  guardianId: z.string().uuid(),
  status: z.enum(["HEALTHY", "ILL", "RECOVERING", "CRITICAL"]),
  createdAt: z.string(),
  updatedAt: z.string(),
});
export type PatientSchema = z.infer<typeof patientSchema>;
