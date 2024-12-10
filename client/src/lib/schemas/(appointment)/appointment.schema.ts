import { z } from "zod";
export const appointmentSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  appointmentDate: z.string(),
  reason: z.string(),
  address: z.string(),
  status: z.enum(["SCHEDULED", "COMPLETED", "CANCELED"]),
  notes: z.string(),
});
export type AppointmentSchema = z.infer<typeof appointmentSchema>;
