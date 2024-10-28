import { signInApi } from "@/lib/apis/sign-in.api";
import {
  SignInErrorResponseSchema,
  SignInRequestSchema,
  SignInResponseSchema,
} from "@/lib/schemas/sign-in.schema";
import { useMutation } from "@tanstack/react-query";

export function useSignInMutation() {
  return useMutation<
    SignInResponseSchema,
    SignInErrorResponseSchema,
    SignInRequestSchema
  >({
    mutationKey: ["sign-in"],
    mutationFn: (body) => signInApi(body),
    onSuccess: (data) => {
      console.log(data);
    },
  });
}
