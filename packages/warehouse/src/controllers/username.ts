import { Username } from "@prisma/client";
import { prisma } from ".";

export const getUsername = async (name: string): Promise<Username | null> =>
  await prisma.username.findUnique({
    where: {
      name
    }
  });

export const getUsernames = async (ownerId: string): Promise<Username[]> =>
  await prisma.username.findMany({
    where: {
      ownerId
    }
  });

export const updateUsername = async (name: string, data: Partial<Username>) =>
  await prisma.username.update({
    where: {
      name
    },
    data
  });