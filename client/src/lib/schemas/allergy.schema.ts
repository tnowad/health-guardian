import { z } from "zod";
export const allergySchema = z.object({
    id: z.string().uuid(),
    userId: z.string().uuid(), // Assuming userId is stored as a UUID string
    allergyName: z.string().min(1, "Allergy name is required"), // At least 1 character
    severity: z.string().optional(), // Optional field
    reactionDescription: z.string().optional(), // Optional field
});
export type AllergySchema = z.infer<typeof allergySchema>;