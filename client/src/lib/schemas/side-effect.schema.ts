import { z } from "zod";
export const sideEffectSchema = z.object({
  id: z.string().uuid(),
  name: z.string(),
  severity: z.enum(["MILD", "MODERATE", "SEVERE"]),
  description: z.string().optional(),
});
export type SideEffectSchema = z.infer<typeof sideEffectSchema>;
