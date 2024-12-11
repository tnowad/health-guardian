import { z } from "zod";

export const diagnosticResultSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  testName: z.string(),
  resultDate: z.string().datetime(),
  resultValue: z.string(),
  notes: z.string().optional(),
});

export type DiagnosticResultSchema = z.infer<typeof diagnosticResultSchema>;

