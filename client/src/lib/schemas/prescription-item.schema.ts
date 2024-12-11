import { z } from "zod";

// Prescription Item Schema
export const prescriptionItemSchema = z.object({
  id: z.string().uuid(),
  prescriptionId: z.string().uuid(), // corresponds to the Prescription entity relationship
  dosage: z.string().optional(),
  medicationName: z.string(),
  image: z.string().optional(),
  frequency: z.string().optional(),
  startDate: z.string().refine((date) => !isNaN(Date.parse(date)), {
    message: "Invalid start date format",
  }),
  endDate: z.string().refine((date) => !isNaN(Date.parse(date)), {
    message: "Invalid end date format",
  }),
  status: z.enum(["ACTIVE", "COMPLETED", "EXPIRED"]),
});

// Type for PrescriptionItem Schema
export type PrescriptionItemSchema = z.infer<typeof prescriptionItemSchema>;
