import { z } from "zod";

// Schema for PastCondition entity
export const pastConditionSchema = z.object({
    id: z.string().uuid(),
    userId: z.string().uuid(), // Assuming userId is stored as a UUID string
    condition: z.string().min(1, "Condition is required"), // At least 1 character
    description: z.string().optional(), // Optional field
    dateDiagnosed: z.string().datetime().optional(), // Assuming ISO date-time format
});
export type PastConditionSchema = z.infer<typeof pastConditionSchema>;