import { getQueryClient } from "@/app/get-query-client";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { z } from "zod";
import { apiClient } from "../client";

export const uploadFileBodySchema = z.object({
  file: z.instanceof(File),
});

export type UploadFileBodySchema = z.infer<typeof uploadFileBodySchema>;

export const uploadFileResponseSchema = z.object({
  id: z.string(),
  url: z.string(),
});

export type UploadFileResponseSchema = z.infer<typeof uploadFileResponseSchema>;

export async function uploadFileApi(
  body: UploadFileBodySchema,
): Promise<UploadFileResponseSchema> {
  const formData = new FormData();
  formData.append("file", body.file);

  const response = await apiClient.post<UploadFileResponseSchema>(
    `/files/upload`,
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    },
  );

  return uploadFileResponseSchema.parse(response.data);
}

export function useUploadFileMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["upload-file"] as const;

  return useMutation<UploadFileResponseSchema, unknown, UploadFileBodySchema>({
    mutationKey,
    mutationFn: (body) => uploadFileApi(uploadFileBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["files"] });
    },
    throwOnError: isAxiosError,
  });
}
