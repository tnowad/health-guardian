import { z } from "zod";
export const diagnosticReportSchema = z.object({
    id: z.string().uuid(), // UUID for the report ID
    userId: z.string().uuid(), // User ID, should match the user entity ID
    reportDate: z.string().datetime(), // Report date in ISO string format
    reportType: z.string().optional(), // Optional report type
    summary: z.string().optional(), // Optional summary
    notes: z.string().optional(), // Optional notes
  });

export type DiagnosticReportSchema = z.infer<typeof diagnosticReportSchema>;