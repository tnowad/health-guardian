import {
  signInApi,
  SignInErrorResponse,
  SignInRequest,
  SignInResponse,
} from "@/lib/api/sign-in.api";
import { useMutation } from "@tanstack/react-query";

export function useSignInMutation() {
  return useMutation<SignInResponse, SignInErrorResponse, SignInRequest>({
    mutationKey: ["sign-in"],
    mutationFn: (data) => signInApi(data),
    onSuccess: (data) => {
      console.log(data);
    },
  });
}
