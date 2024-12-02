import { z } from "zod";
import { apiClient } from "../client";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { getQueryClient } from "@/app/get-query-client";

export const deleteAppointmentParamsSchema = z.object({
  appointmentId: z.string().uuid(),
});
export type DeleteAppointmentParamsSchema = z.infer<
  typeof deleteAppointmentParamsSchema
>;

export const deleteAppointmentResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});
export type DeleteAppointmentResponseSchema = z.infer<
  typeof deleteAppointmentResponseSchema
>;

export const deleteAppointmentErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema],
);
export type DeleteAppointmentErrorResponseSchema = z.infer<
  typeof deleteAppointmentErrorResponseSchema
>;

export async function deleteAppointmentApi(
  params: DeleteAppointmentParamsSchema,
): Promise<DeleteAppointmentResponseSchema> {
  const response = await apiClient.delete<DeleteAppointmentResponseSchema>(
    `/appointments/${params.appointmentId}`,
  );
  return deleteAppointmentResponseSchema.parse(response.data);
}

export function useDeleteAppointmentMutation(
  params: DeleteAppointmentParamsSchema,
) {
  const mutationKey = ["delete-appointment", params] as const;
  const queryClient = getQueryClient();

  return useMutation<
    DeleteAppointmentResponseSchema,
    DeleteAppointmentErrorResponseSchema,
    void
  >({
    mutationKey,
    mutationFn: () =>
      deleteAppointmentApi(deleteAppointmentParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ["appointments"],
      });
    },
    throwOnError: isAxiosError,
  });
}
