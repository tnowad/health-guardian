import { useMutation } from "@tanstack/react-query";
import { z } from "zod";
import { isAxiosError } from "axios";
import { getQueryClient } from "@/app/get-query-client";
import { notificationSchema } from "@/lib/schemas/(notification)/notification.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";

export const updateStatusNotificationParamsSchema = z.object({
  Id: z.string().uuid(),
});
export type UpdateStatusNotificationParamsSchema = z.infer<
  typeof updateStatusNotificationParamsSchema
>;

export const updateStatusNotificationBodySchema = notificationSchema.pick({
  readStatus: true,
});
export type UpdateStatusNotificationBodySchema = z.infer<
  typeof updateStatusNotificationBodySchema
>;

export const updateStatusNotificationResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type UpdateStatusNotificationResponseSchema = z.infer<
  typeof updateStatusNotificationResponseSchema
>;

export const updateStatusNotificationErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);

export type UpdateStatusNotificationErrorResponseSchema = z.infer<
  typeof updateStatusNotificationErrorResponseSchema
>;

export async function updateStatusNotificationApi(
  params: UpdateStatusNotificationParamsSchema,
  body: UpdateStatusNotificationBodySchema
): Promise<UpdateStatusNotificationResponseSchema> {
  const response = await apiClient.put<UpdateStatusNotificationResponseSchema>(
    `/notifications/${params.Id}/read-status`,
    body
  );
  return response.data;
}

export function useUpdateStatusNotificationMutation(
  params: UpdateStatusNotificationParamsSchema
) {
  const mutationKey = ["update-status-notification", params] as const;
  const queryClient = getQueryClient();

  return useMutation<
    UpdateStatusNotificationResponseSchema,
    UpdateStatusNotificationErrorResponseSchema,
    UpdateStatusNotificationBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateStatusNotificationApi(
        params,
        updateStatusNotificationBodySchema.parse(body)
      ),
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ["notifications"],
      });
    },
    throwOnError: (error) => isAxiosError(error),
  });
}
