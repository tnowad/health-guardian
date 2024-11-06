import { z } from "zod";
export const reportedSideEffectSchema = z.object({
  id: z.string().uuid(),
  patientId: z.string().uuid(),
  sideEffectId: z.string().uuid(),
  prescriptionId: z.string().uuid(),
  reportDate: z.string(),
  severity: z.enum(["MILD", "MODERATE", "SEVERE"]),
  notes: z.string().optional(),
  reportedBy: z.string().optional(),
  outcome: z.string().optional(),
});
export type ReportedSideEffectSchema = z.infer<typeof reportedSideEffectSchema>;
