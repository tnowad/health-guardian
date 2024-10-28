import { setCookie } from "cookies-next";

import { signInApi } from "@/lib/apis/sign-in.api";
import {
  SignInErrorResponseSchema,
  SignInRequestSchema,
  SignInResponseSchema,
} from "@/lib/schemas/sign-in.schema";
import { useMutation } from "@tanstack/react-query";
import { getQueryClient } from "@/app/get-query-client";

export function useSignInMutation() {
  const queryClient = getQueryClient();

  return useMutation<
    SignInResponseSchema,
    SignInErrorResponseSchema,
    SignInRequestSchema
  >({
    mutationKey: ["sign-in"],
    mutationFn: (body) => signInApi(body),
    onSuccess: (data) => {
      setCookie("access_token", data.tokens.accessToken);
      setCookie("refresh_token", data.tokens.refreshToken);
      queryClient.invalidateQueries({
        queryKey: ["me"],
      });
    },
  });
}
