import { z } from "zod";

export const pageableRequestSchema = z.object({
  page: z.number().int().optional(),
  size: z.number().int().optional(),
  sortFields: z.array(z.string()).optional(),
  decs: z.array(z.boolean()).optional(),
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

export const pageableResponseSchema = z.object({
  content: z.array(z.any()),
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

export function createListResponseSchema<ItemType extends z.ZodTypeAny>(
  schema: ItemType,
) {
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
