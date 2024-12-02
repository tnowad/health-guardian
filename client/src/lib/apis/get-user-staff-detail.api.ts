import { queryOptions } from "@tanstack/react-query";
import { z } from "zod";
import { apiClient } from "../client";
import { userStaffSchema } from "../schemas/user-staff.schema";

export const getUserStaffDetailResponseSchema = userStaffSchema;

export type GetUserStaffDetailResponseSchema = z.infer<
  typeof getUserStaffDetailResponseSchema
>;

export async function getUserStaffDetailApi(
  id: string,
): Promise<GetUserStaffDetailResponseSchema> {
  const response = await apiClient.get<GetUserStaffDetailResponseSchema>(
    `/user-staff/${id}`,
  );
  return getUserStaffDetailResponseSchema.parse(response.data);
}

export function useGetUserStaffDetailQuery(id: string) {
  const queryKey = ["user-staff-detail", id] as const;
  return queryOptions<GetUserStaffDetailResponseSchema>({
    queryKey,
    enabled: !!id,
    queryFn: () => getUserStaffDetailApi(id),
  });
}
