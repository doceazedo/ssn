import { Username } from "@prisma/client";
import { prisma } from ".";

export const getUsername = async (name: string): Promise<Username | null> =>
  await prisma.username.findFirst({
    where: {
      name: {
        equals: name,
        mode: 'insensitive'
      }
    },
  });

export const getUsernames = async (ownerId: string): Promise<Username[]> =>
  await prisma.username.findMany({
    where: {
      ownerId
    },
    orderBy: {
      createdAt: 'asc'
    }
  });

export const updateUsername = async (name: string, data: Partial<Username>): Promise<Username> =>
  await prisma.username.update({
    where: {
      name
    },
    data
  });

export const createUsername = async (name: string, ownerId: string): Promise<Username | null> =>
  await prisma.username.create({
    data: {
      name,
      ownerId
    }
  });