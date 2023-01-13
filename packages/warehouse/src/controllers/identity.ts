import { prisma } from '.';
import type { Identity, Role, Username } from '@prisma/client';

export type IdentityWithUsernames = Identity & { usernames: Username[] };

export const getUser = async (uuid: string): Promise<IdentityWithUsernames | null> =>
  await prisma.identity.findUnique({
    where: {
      uuid
    },
    include: {
      usernames: true
    }
  });

export const getUserByEmail = async (email: string): Promise<IdentityWithUsernames | null> =>
  await prisma.identity.findFirst({
    where: {
      email: {
        equals: email,
        mode: 'insensitive'
      }
    },
    include: {
      usernames: true
    }
  });

export const getUserByName = async (name: string): Promise<IdentityWithUsernames | null> => {
  const username = await prisma.username.findFirst({
    where: {
      name: {
        equals: name,
        mode: 'insensitive'
      }
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

export const getUserByPasswordResetToken = async (passwordResetToken: string): Promise<IdentityWithUsernames | null> =>
  await prisma.identity.findFirst({
    where: {
      passwordResetToken
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

export const updateUserRole = async (uuid: string, role: Role): Promise<IdentityWithUsernames | null> => {
  try {
    const user = await prisma.identity.update({
      where: {
        uuid
      },
      data: {
        role
      },
      include: {
        usernames: true
      }
    });
    return user;
  } catch (e) {
    return null;
  }
}

export const updateUser = async (
  uuid: string, 
  data: Partial<Identity>
): Promise<IdentityWithUsernames | null> =>{
  try {
    const user = await prisma.identity.update({
      where: {
        uuid
      },
      data,
      include: {
        usernames: true
      }
    });
    return user;
  } catch (e) {
    return null;
  }
}
