import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { userMedicalStaffSchema } from "../schemas/user-medical-staff.schema";

export const getUserMedicalStaffDetailResponseSchema = userMedicalStaffSchema;

export type GetUserMedicalStaffDetailResponseSchema = z.infer<
  typeof getUserMedicalStaffDetailResponseSchema
>;

export async function getUserMedicalStaffDetailApi(
  id: string,
): Promise<GetUserMedicalStaffDetailResponseSchema> {
  const response = await apiClient.get<GetUserMedicalStaffDetailResponseSchema>(
    `/user-medical-staff/${id}`,
  );
  return getUserMedicalStaffDetailResponseSchema.parse(response.data);
}

export function useGetUserMedicalStaffDetailQuery(id: string) {
  const queryKey = ["user-medical-staff-detail", id] as const;
  return queryOptions<GetUserMedicalStaffDetailResponseSchema>({
    queryKey,
    enabled: !!id,
    queryFn: () => getUserMedicalStaffDetailApi(id),
  });
}
