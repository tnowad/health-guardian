import { z } from "zod";
export const notificationSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  type: z.enum(["REMINDER", "ALERT", "INFO", "WARNING"]),
  notificationDate: z.string(),
  readStatus: z.boolean().optional(),
});
export type NotificationSchema = z.infer<typeof notificationSchema>;
