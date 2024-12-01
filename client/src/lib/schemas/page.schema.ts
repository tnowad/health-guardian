import { z } from "zod";

export const pageableRequestSchema = z.object({
  page: z.number().int(),
  size: z.number().int(),
  sortFields: z.array(z.string()),
  decs: z.array(z.boolean()),
});

export const sortSchema = z.object({
  sorted: z.boolean(),
  unsorted: z.boolean(),
  empty: z.boolean(),
});

export const pageableSchema = z.object({
  sort: sortSchema,
  offset: z.number().int(),
  pageNumber: z.number().int(),
  pageSize: z.number().int(),
  paged: z.boolean(),
  unpaged: z.boolean(),
});

export function createListResponseSchema<T extends z.ZodTypeAny>(schema: T) {
  return z.object({
    content: z.array(schema),
    pageable: pageableSchema,
    totalPages: z.number().int(),
    totalElements: z.number().int(),
    last: z.boolean(),
    size: z.number().int(),
    number: z.number().int(),
    sort: sortSchema,
    numberOfElements: z.number().int(),
    first: z.boolean(),
    empty: z.boolean(),
  });
}
