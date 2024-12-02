import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";
import { hospitalSchema } from "../schemas/hospital.schema";

export const createHospitalBodySchema = hospitalSchema.omit({
  id: true,
});

export type CreateHospitalBodySchema = z.infer<typeof createHospitalBodySchema>;

export const createHospitalResponseSchema = hospitalSchema;

export type CreateHospitalResponseSchema = z.infer<
  typeof createHospitalResponseSchema
>;

export async function createHospitalApi(
  body: CreateHospitalBodySchema,
): Promise<CreateHospitalResponseSchema> {
  const response = await apiClient.post<CreateHospitalResponseSchema>(
    `/hospitals`,
    body,
  );
  return createHospitalResponseSchema.parse(response.data);
}

export function useCreateHospitalMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["create-hospital"] as const;

  return useMutation<
    CreateHospitalResponseSchema,
    unknown,
    CreateHospitalBodySchema
  >({
    mutationKey,
    mutationFn: (body) =>
      createHospitalApi(createHospitalBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["hospitals"] });
    },
    throwOnError: isAxiosError,
  });
}
