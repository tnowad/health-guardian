import { z } from "zod";

export const surgerySchema = z.object({
    id: z.string().uuid(),
    userId: z.string().uuid(), // Assuming userId is stored as a UUID string
    date: z.string().datetime().optional(), // Assuming ISO date-time format
    description: z.string().min(1, "Description is required"), // At least 1 character
    notes: z.string().optional(), // Optional field
});
export type SurgerySchema = z.infer<typeof surgerySchema>;