import {
  QueryClient,
  defaultShouldDehydrateQuery,
  isServer,
} from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { ZodError } from "zod";

function makeQueryClient() {
  return new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 60 * 1000,
        throwOnError: (error) => {
          if (isAxiosError(error)) return true;
          if (error instanceof ZodError) {
            console.error(
              "ZodError",
              error.errors.map((e) => e.message),
              error,
            );
          }
          return false;
        },
      },
      mutations: {
        throwOnError: (error) => {
          if (isAxiosError(error)) return true;
          if (error instanceof ZodError) {
            console.error(
              "ZodError",
              error.errors.map((e) => e.message),
              error,
            );
          }
          return false;
        },
      },
      dehydrate: {
        shouldDehydrateQuery: (query) =>
          defaultShouldDehydrateQuery(query) ||
          query.state.status === "pending",
      },
    },
  });
}

let browserQueryClient: QueryClient | undefined = undefined;

export function getQueryClient() {
  if (isServer) {
    return makeQueryClient();
  } else {
    if (!browserQueryClient) browserQueryClient = makeQueryClient();
    return browserQueryClient;
  }
}
