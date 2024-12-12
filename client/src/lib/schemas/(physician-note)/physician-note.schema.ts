import { z } from "zod";
export const physicianNoteSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  date: z.string(),
  note: z.string(),
});
export type PhysicianNoteSchema = z.infer<typeof physicianNoteSchema>;
