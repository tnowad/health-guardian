import {z} from 'zod';

export const questionRequestSchema = z.object({
    question: z.string().min(1, 'Question must not be empty'),
});

export const questionResponseSchema = z.string();
