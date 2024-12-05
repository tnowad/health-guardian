import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { appointmentSchema } from "../schemas/appointment.schema";
import { unauthorizedResponseSchema } from "../schemas/error.schema";
import { isAxiosError } from "axios";

export const updateAppointmentBodySchema = appointmentSchema.partial().extend({
  id: z.string(),
});

export type UpdateAppointmentBodySchema = z.infer<
  typeof updateAppointmentBodySchema
>;

export const updateAppointmentResponseSchema = appointmentSchema;

export type UpdateAppointmentResponseSchema = z.infer<
  typeof updateAppointmentResponseSchema
>;

export const updateAppointmentErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);

export type UpdateAppointmentErrorResponseSchema = z.infer<
  typeof updateAppointmentErrorResponseSchema
>;

export async function updateAppointmentApi(
  body: UpdateAppointmentBodySchema
): Promise<UpdateAppointmentResponseSchema> {
  const response = await apiClient.put<UpdateAppointmentResponseSchema>(
    `/appointments/${body.id}`,
    body
  );
  return updateAppointmentResponseSchema.parse(response.data);
}

export function useUpdateAppointmentMutation() {
  const mutationKey = ["update-appointment"] as const;
  const queryClient = getQueryClient();

  return useMutation<
    UpdateAppointmentResponseSchema,
    UpdateAppointmentErrorResponseSchema,
    UpdateAppointmentBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateAppointmentApi(updateAppointmentBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ["appointments"],
      });
    },
    throwOnError: (error) => isAxiosError(error),
  });
}
