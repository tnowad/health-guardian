import { z } from "zod";

export const diagnosticResultSchema = z.object({
    id: z.string().uuid(), // UUID for the result ID
    userId: z.string().uuid(), // User ID, should match the user entity ID
    testName: z.string(), // Name of the test, required field
    resultDate: z.string().datetime(), // Result date in ISO string format
    resultValue: z.string(), // Result value, required field
    notes: z.string().optional(), // Optional notes
  });
  
  export type DiagnosticResultSchema = z.infer<typeof diagnosticResultSchema>;