import { apiClient } from "../client";
import {
  SignInRequestSchema,
  SignInResponseSchema,
} from "../schemas/sign-in.schema";

export async function signInApi(
  body: SignInRequestSchema,
): Promise<SignInResponseSchema> {
  const response = await apiClient.post<SignInResponseSchema>(
    "/auth/sign-in",
    body,
  );

  return response.data;
}
