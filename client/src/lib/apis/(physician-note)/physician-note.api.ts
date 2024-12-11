import { z } from "zod";
import { queryOptions } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import {
  createListResponseSchema,
  pageableRequestSchema,
} from "@/lib/schemas/page.schema";
import { unauthorizedResponseSchema } from "@/lib/schemas/error.schema";
import { apiClient } from "@/lib/client";
import { physicianNoteSchema } from "@/lib/schemas/(physician-note)/physician-note.schema";

export const listPhysicianNotesQuerySchema = pageableRequestSchema.extend({
  userId: z.string().uuid().optional(),
  date: z.string().optional(),
  note: z.string().optional(),
});
export type ListPhysicianNotesQuerySchema = z.infer<
  typeof listPhysicianNotesQuerySchema
>;

export const listPhysicianNotesResponseSchema =
  createListResponseSchema(physicianNoteSchema);
export type ListPhysicianNotesResponseSchema = z.infer<
  typeof listPhysicianNotesResponseSchema
>;

export const listPhysicianNotesErrorResponseSchema = z.discriminatedUnion(
  "type",
  [unauthorizedResponseSchema]
);
export type ListPhysicianNotesErrorResponseSchema = z.infer<
  typeof listPhysicianNotesErrorResponseSchema
>;

export async function listPhysicianNotesApi(
  query: ListPhysicianNotesQuerySchema
) {
  const response = await apiClient.get<ListPhysicianNotesResponseSchema>(
    "/physician-notes",
    query
  );
  return response.data;
}

export function createListPhysicianNotesQueryOptions(
  query: ListPhysicianNotesQuerySchema
) {
  const queryKey = ["physician-notes", query] as const;
  return queryOptions<ListPhysicianNotesResponseSchema>({
    queryKey,
    queryFn: () =>
      listPhysicianNotesApi(listPhysicianNotesQuerySchema.parse(query)),
    throwOnError: isAxiosError,
  });
}
