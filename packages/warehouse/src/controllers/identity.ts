import { prisma } from '.';
import type { Identity, Username } from '@prisma/client';

export type IdentityWithUsernames = Identity & { usernames: Username[] };

export const getUserByEmail = async (email: string): Promise<IdentityWithUsernames | null> =>
  await prisma.identity.findUnique({
    where: {
      email
    },
    include: {
      usernames: true
    }
  });

export const getUserByName = async (name: string): Promise<IdentityWithUsernames | null> => {
  const username = await prisma.username.findUnique({
    where: {
      name
    },
    include: {
      owner: {
        include: {
          usernames: true
        }
      }
    }
  });
  return username?.owner || null;
};

export const getUserByToken = async (token: string): Promise<IdentityWithUsernames | null> =>
  await prisma.identity.findUnique({
    where: {
      token
    },
    include: {
      usernames: true
    }
  });

export const getUserByEmailOrName = async (usernameOrEmail: string) =>
  await getUserByEmail(usernameOrEmail) || await getUserByName(usernameOrEmail);

export const createUser = async (data: any): Promise<IdentityWithUsernames | null> => {
  let user;
  try {
    user = await prisma.identity.create({
      data,
      include: {
        usernames: true
      }
    });
  } catch (e) {
    return null;
  }
  return user || null;
}
