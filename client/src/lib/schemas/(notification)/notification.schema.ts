import { z } from "zod";
export const notificationSchema = z.object({
  id: z.string().uuid(),
  userId: z.string().uuid(),
  title: z.string(),
  notificationType: z.enum(["REMINDER", "ALERT", "INFO", "WARNING"]),
  notificationDate: z.string(),
  readStatus: z.boolean().optional(),
});
export type NotificationSchema = z.infer<typeof notificationSchema>;
