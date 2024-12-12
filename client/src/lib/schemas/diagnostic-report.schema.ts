import { z } from "zod";
export const diagnosticReportSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  reportDate: z.coerce.string().date(),
  reportType: z.enum([
    "X_RAY",
    "CT_SCAN",
    "MRI",
    "BLOOD_TEST",
    "ECG",
    "GENERAL_CHECKUP",
  ]),
  files: z.array(z.string()).optional(),
  summary: z.string().optional(),
  notes: z.string().optional(),
});

export type DiagnosticReportSchema = z.infer<typeof diagnosticReportSchema>;
