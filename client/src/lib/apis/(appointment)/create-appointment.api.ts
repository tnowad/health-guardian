import { z } from "zod";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { getQueryClient } from "@/app/get-query-client";
import { appointmentSchema } from "@/lib/schemas/(appointment)/appointment.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";

export const createAppointmentBodySchema = appointmentSchema.omit({
  id: true,
});

export type CreateAppointmentBodySchema = z.infer<
  typeof createAppointmentBodySchema
>;

export const createAppointmentResponseSchema = appointmentSchema;
export type CreateAppointmentResponseSchema = z.infer<
  typeof createAppointmentResponseSchema
>;

export const createAppointmentErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type CreateAppointmentErrorResponseSchema = z.infer<
  typeof createAppointmentErrorResponseSchema
>;

export async function createAppointmentApi(
  body: CreateAppointmentBodySchema
): Promise<CreateAppointmentResponseSchema> {
  const response = await apiClient.post<CreateAppointmentResponseSchema>(
    `/appointments`,
    body
  );
  return createAppointmentResponseSchema.parse(response.data);
}

export function useCreateAppointmentMutation() {
  const mutationKey = ["create-appointment"] as const;
  const queryClient = getQueryClient();
  return useMutation<
    CreateAppointmentResponseSchema,
    CreateAppointmentErrorResponseSchema,
    CreateAppointmentBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createAppointmentApi(createAppointmentBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ["appointments"],
      });
    },
    throwOnError: isAxiosError,
  });
}
