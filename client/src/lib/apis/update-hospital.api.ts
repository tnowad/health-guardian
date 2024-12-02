import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { hospitalSchema } from "../schemas/hospital.schema";

export const updateHospitalBodySchema = hospitalSchema
  .omit({
    id: true,
  })
  .extend({
    id: z.string().uuid(),
  });

export type UpdateHospitalBodySchema = z.infer<typeof updateHospitalBodySchema>;

export const updateHospitalResponseSchema = hospitalSchema;

export type UpdateHospitalResponseSchema = z.infer<
  typeof updateHospitalResponseSchema
>;

export async function updateHospitalApi(
  body: UpdateHospitalBodySchema,
): Promise<UpdateHospitalResponseSchema> {
  const response = await apiClient.put<UpdateHospitalResponseSchema>(
    `/hospitals/${body.id}`,
    body,
  );
  return updateHospitalResponseSchema.parse(response.data);
}

export function useUpdateHospitalMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["update-hospital"] as const;

  return useMutation<
    UpdateHospitalResponseSchema,
    unknown,
    UpdateHospitalBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      updateHospitalApi(updateHospitalBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["hospitals"] });
    },
    throwOnError: isAxiosError,
  });
}
