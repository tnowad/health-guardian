import { z } from "zod";

export const immunizationSchema = z.object({
    id: z.string().uuid(),
    userId: z.string().uuid(), // Assuming userId is stored as a UUID string
    vaccinationDate: z.string().datetime().optional(), // Assuming ISO date-time format
    vaccineName: z.string().min(1, "Vaccine name is required"), // At least 1 character
    batchNumber: z.string().optional(), // Optional field
    notes: z.string().optional(), // Optional field
});
export type ImmunizationSchema = z.infer<typeof immunizationSchema>;