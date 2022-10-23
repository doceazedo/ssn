import { PrismaClient } from '@prisma/client';

export const prisma = new PrismaClient();

export * from './connection';
export * from './identity';
export * from './invite';
export * from './username';
