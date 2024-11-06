import { z } from "zod";
export const aggregatedSideEffectSchema = z.object({
  id: z.string().uuid(),
  sideEffectId: z.string().uuid(),
  medicationId: z.string().uuid(),
  totalReports: z.number(),
  periodStart: z.string(),
  periodEnd: z.string(),
});
export type AggregatedSideEffectSchema = z.infer<
  typeof aggregatedSideEffectSchema
>;
