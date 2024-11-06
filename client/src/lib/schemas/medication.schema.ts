import { z } from "zod";
export const medicationSchema = z.object({
  id: z.string().uuid(),
  name: z.string(),
  activeIngredient: z.string().optional(),
  dosageForm: z.string().optional(),
  standardDosage: z.string().optional(),
  manufacturer: z.string().optional(),
});
export type MedicationSchema = z.infer<typeof medicationSchema>;
