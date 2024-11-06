import { z } from "zod";
export const prescriptionSchema = z.object({
  id: z.string().uuid(),
  patientId: z.string().uuid(),
  medicationId: z.string().uuid(),
  prescribedBy: z.string().uuid(),
  dosage: z.string(),
  frequency: z.string(),
  startDate: z.string(),
  endDate: z.string(),
  status: z.enum(["ACTIVE", "COMPLETED", "EXPIRED"]),
});
export type PrescriptionSchema = z.infer<typeof prescriptionSchema>;
