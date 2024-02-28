import { PrismaClient } from "@prisma/client";

export const prisma = new PrismaClient();

export * from "./connection";
export * from "./donation";
export * from "./identity";
export * from "./invite";
export * from "./profile";
export * from "./username";
