import { z } from "zod";
export const visitSummarySchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  visitDate: z.string(),
  visitType: z.enum([
    "CHECKUP",
    "FOLLOW_UP",
    "EMERGENCY",
    "CONSULTATION",
    "SURGERY",
    "THERAPY",
  ]),
  summary: z.string(),
  notes: z.string(),
});
export type VisitSummarySchema = z.infer<typeof visitSummarySchema>;
