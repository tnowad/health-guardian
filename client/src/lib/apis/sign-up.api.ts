import { z } from "zod";
import { useMutation } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { apiClient } from "../client";
import { getQueryClient } from "@/app/get-query-client";

export const signUpBodySchema = z
  .object({
    firstName: z.string().min(2, "First name must be at least 2 characters."),
    lastName: z.string().min(2, "Last name must be at least 2 characters."),
    dateOfBirth: z.date({ required_error: "Date of birth is required." }),
    gender: z.enum(["MALE", "FEMALE", "OTHER"], {
      required_error: "Please select a gender.",
    }),
    email: z.string().email("Invalid email address."),
    password: z.string().min(8, "Password must be at least 8 characters."),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match.",
    path: ["confirmPassword"],
  });

export type SignUpBodySchema = z.infer<typeof signUpBodySchema>;

export const signUpResponseSchema = z.object({
  message: z.string(),
});

export type SignUpResponseSchema = z.infer<typeof signUpResponseSchema>;

export async function signUpApi(
  body: SignUpBodySchema,
): Promise<SignUpResponseSchema> {
  const response = await apiClient.post<SignUpResponseSchema>("/sign-up", body);
  return response.data;
}

export function useSignUpMutation() {
  const queryClient = getQueryClient();
  const mutationKey = ["sign-up"] as const;

  return useMutation<SignUpResponseSchema, unknown, SignUpBodySchema>({
    mutationKey,
    mutationFn: (body) => signUpApi(signUpBodySchema.parse(body)),
    onSuccess() {
      queryClient.invalidateQueries({ queryKey: ["user-profile"] });
    },
    throwOnError: isAxiosError,
  });
}
