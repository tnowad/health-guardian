import { z } from "zod";

export const prescriptionItemSchema = z.object({
  id: z.string().uuid(),
  prescriptionId: z.string().uuid(),
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

export type PrescriptionItemSchema = z.infer<typeof prescriptionItemSchema>;
