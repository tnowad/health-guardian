import { z } from "zod";
export const consentFormSchema = z.object({
  id: z.string().uuid(),
  patientId: z.string().uuid(),
  formName: z.string(),
  consentDate: z.string(),
  status: z.enum(["GRANTED", "REVOKED"]),
});
export type ConsentFormSchema = z.infer<typeof consentFormSchema>;
