import { z } from "zod";
export const appointmentSchema = z.object({
  id: z.string().uuid(),
  patientId: z.string().uuid(),
  doctorId: z.string().uuid(),
  appointmentDate: z.string(),
  reasonForVisit: z.string(),
  status: z.enum(["SCHEDULED", "COMPLETED", "CANCELED"]),
});
export type AppointmentSchema = z.infer<typeof appointmentSchema>;
