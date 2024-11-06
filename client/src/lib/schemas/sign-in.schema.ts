import { z } from "zod";
import { localAccountProviderRequestSchema } from "./account-provider";
import { tokensResponseSchema } from "./jwt.schema";
import {
  networkErrorSchema,
  unknownErrorSchema,
  validationErrorResponseSchema,
} from "./error.schema";

export const signInRequestSchema = localAccountProviderRequestSchema;
export const signInResponseSchema = z.object({
  message: z.string(),
  tokens: tokensResponseSchema,
});
export const signInErrorResponseSchema = z.discriminatedUnion("type", [
  validationErrorResponseSchema,
  unknownErrorSchema,
  networkErrorSchema,
]);

export type SignInRequestSchema = z.infer<typeof signInRequestSchema>;
export type SignInResponseSchema = z.infer<typeof signInResponseSchema>;
export type SignInErrorResponseSchema = z.infer<
  typeof signInErrorResponseSchema
>;
