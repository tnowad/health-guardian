import { z } from "zod";
export const prescriptionSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  issuedBy: z.string(),
  validUntil: z.string(),
  issuedDate: z.string(),
  status: z.enum(["ACTIVE", "COMPLETED", "EXPIRED"]),
});
export type PrescriptionSchema = z.infer<typeof prescriptionSchema>;
