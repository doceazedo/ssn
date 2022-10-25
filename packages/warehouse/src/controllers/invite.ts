import { prisma } from '.';
import { generateSlug } from 'random-word-slugs';
import type { Invite } from '@prisma/client';

export type InviteWithUsedBy = Invite & {
  usedBy: {
    primaryUsername: string
  } | null
}

export const getInvite = async (code: string): Promise<Invite | null> =>
  await prisma.invite.findUnique({
    where: {
      code
    }
  });

export const getUserInvites = async (ownerId: string): Promise<InviteWithUsedBy[]> =>
  await prisma.invite.findMany({
    where: {
      ownerId
    },
    include: {
      usedBy: {
        select: {
          primaryUsername: true
        }
      }
    }
  });

export const generateUserInvites = async (quantity: number) => ({
  create: Array(quantity).fill(null).map(_ => ({
    code: generateSlug(),
  })),
})

export const updateInvite = async (code: string, data: Partial<Invite>) =>
  await prisma.invite.update({
    where: {
      code
    },
    data
  });
