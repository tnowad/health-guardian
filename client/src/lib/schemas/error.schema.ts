import { z } from "zod";

export const validationErrorResponseSchema = z.object({
  type: z.literal("ValidationError"),
  message: z.string(),
  errors: z.record(z.array(z.string())),
});

export const invalidTokenResponseSchema = z.object({
  type: z.literal("InvalidTokenError"),
  message: z.string(),
});
export const expiredTokenResponseSchema = z.object({
  type: z.literal("ExpiredTokenError"),
  message: z.string(),
});
export const unauthorizedResponseSchema = z.object({
  type: z.literal("UnauthorizedError"),
  message: z.string(),
});
export const forbiddenResponseSchema = z.object({
  type: z.literal("ForbiddenError"),
  message: z.string(),
});
export const insufficientPermissionsResponseSchema = z.object({
  type: z.literal("InsufficientPermissionsError"),
  message: z.string(),
});

export const badRequestResponseSchema = z.object({
  type: z.literal("BadRequestError"),
  message: z.string(),
});
export const methodNotAllowedResponseSchema = z.object({
  type: z.literal("MethodNotAllowedError"),
  message: z.string(),
});
export const unsupportedMediaTypeResponseSchema = z.object({
  type: z.literal("UnsupportedMediaTypeError"),
  message: z.string(),
});
export const tooManyRequestsResponseSchema = z.object({
  type: z.literal("TooManyRequestsError"),
  message: z.string(),
});
export const notFoundResponseSchema = z.object({
  type: z.literal("NotFoundError"),
  message: z.string(),
});

export const externalServiceUnavailableResponseSchema = z.object({
  type: z.literal("ExternalServiceUnavailableError"),
  message: z.string(),
});
export const apiRateLimitExceededResponseSchema = z.object({
  type: z.literal("APIRateLimitExceededError"),
  message: z.string(),
});
export const thirdPartyAuthenticationFailedResponseSchema = z.object({
  type: z.literal("ThirdPartyAuthenticationFailedError"),
  message: z.string(),
});

export const unknownErrorSchema = z.object({
  type: z.literal("UnknownError"),
  message: z.string(),
});
export const networkErrorSchema = z.object({
  type: z.literal("NetworkError"),
  message: z.string(),
});
