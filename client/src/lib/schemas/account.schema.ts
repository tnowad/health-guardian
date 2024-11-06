import { z } from "zod";
export const accountSchema = z.object({
  id: z.string().uuid(),
  profileType: z.string(),
});
export type AccountSchema = z.infer<typeof accountSchema>;
