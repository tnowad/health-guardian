import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const deleteHospitalParamsSchema = z.object({
  id: z.string().uuid(),
});

export type DeleteHospitalParamsSchema = z.infer<
  typeof deleteHospitalParamsSchema
>;

export const deleteHospitalResponseSchema = z.object({
  id: z.string().uuid(),
  message: z.string(),
});

export type DeleteHospitalResponseSchema = z.infer<
  typeof deleteHospitalResponseSchema
>;

export async function deleteHospitalApi(
  params: DeleteHospitalParamsSchema,
): Promise<DeleteHospitalResponseSchema> {
  const response = await apiClient.delete<DeleteHospitalResponseSchema>(
    `/hospitals/${params.id}`,
  );
  return deleteHospitalResponseSchema.parse(response.data);
}

export function useDeleteHospitalMutation(params: DeleteHospitalParamsSchema) {
  const queryClient = getQueryClient();
  const mutationKey = ["delete-hospital", params] as const;

  return useMutation<DeleteHospitalResponseSchema, unknown, void>({
    mutationKey,
    mutationFn: () =>
      deleteHospitalApi(deleteHospitalParamsSchema.parse(params)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["hospitals"] });
    },
    throwOnError: isAxiosError,
  });
}
