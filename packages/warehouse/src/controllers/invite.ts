import { prisma } from '.';
import { generateSlug } from 'random-word-slugs';
import type { Invite } from '@prisma/client';

export const getInvite = async (code: string): Promise<Invite | null> =>
  await prisma.invite.findUnique({
    where: {
      code
    }
  });

export const getUserInvites = async (ownerId: string): Promise<Invite[]> =>
  await prisma.invite.findMany({
    where: {
      ownerId
    }
  });

export const generateUserInvites = async (ownerId: string, quantity: number) =>
  await prisma.invite.createMany({
    data: Array(quantity).fill(null).map(_ => ({
      code: generateSlug(),
      ownerId,
    }))
  });

export const updateInvite = async (code: string, data: Partial<Invite>) =>
  await prisma.invite.update({
    where: {
      code
    },
    data
  });
