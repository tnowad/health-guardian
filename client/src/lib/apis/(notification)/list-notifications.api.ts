import { z } from "zod";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "@/lib/schemas/page.schema";
import { notificationSchema } from "@/lib/schemas/(notification)/notification.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const listNotificationsQuerySchema = pageableRequestSchema.extend({
  userId: z.string().uuid().optional(),
  notificationType: z.string().optional(),
  notificationDate: z.string().optional(),
  readStatus: z.string().optional(),
});
export type ListNotificationsQuerySchema = z.infer<
  typeof listNotificationsQuerySchema
>;

export const listNotificationsResponseSchema =
  createListResponseSchema(notificationSchema);
export type ListNotificationsResponseSchema = z.infer<
  typeof listNotificationsResponseSchema
>;

export const listNotificationsErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type ListNotificationsErrorResponseSchema = z.infer<
  typeof listNotificationsErrorResponseSchema
>;

export async function listNotificationsApi(
  query: ListNotificationsQuerySchema
) {
  const response = await apiClient.get<ListNotificationsResponseSchema>(
    "/notifications",
    query
  );
  return response.data;
}

export function createListNotificationsQueryOptions(
  query: ListNotificationsQuerySchema
) {
  const queryKey = ["notifications", query] as const;
  return queryOptions<ListNotificationsResponseSchema>({
    queryKey,
    queryFn: () =>
      listNotificationsApi(listNotificationsQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
