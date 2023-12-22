import { Badge, Username, UsernameBadges } from "@prisma/client";
import { prisma } from ".";

export type BadgeWithDetails = UsernameBadges & { badge: Badge };

export const getUsername = async (name: string): Promise<Username | null> =>
  await prisma.username.findFirst({
    where: {
      name: {
        equals: name,
        mode: "insensitive",
      },
    },
  });

export const getUsernames = async (ownerId: string): Promise<Username[]> =>
  await prisma.username.findMany({
    where: {
      ownerId,
    },
    orderBy: {
      createdAt: "asc",
    },
  });

export const updateUsername = async (
  name: string,
  data: Partial<Username>
): Promise<Username> =>
  await prisma.username.update({
    where: {
      name,
    },
    data,
  });

export const createUsername = async (
  name: string,
  ownerId: string
): Promise<Username | null> =>
  await prisma.username.create({
    data: {
      name,
      ownerId,
    },
  });

export const deleteUsername = async (name: string) => {
  const deleteBadges = prisma.usernameBadges.deleteMany({
    where: {
      ownerName: name,
    },
  });

  // deleteMany() is needed here, because if we try to
  // delete() a record that does not exist, prism throws
  // an error
  const deleteProfile = prisma.profile.deleteMany({
    where: {
      ownerName: name,
    },
  });

  const deleteUsername = prisma.username.delete({
    where: {
      name,
    },
  });

  return await prisma.$transaction([
    deleteBadges,
    deleteProfile,
    deleteUsername,
  ]);
};

export const getUserBadges = async (
  username: string
): Promise<BadgeWithDetails[]> =>
  await prisma.usernameBadges.findMany({
    where: {
      ownerName: username,
    },
    include: {
      badge: true,
    },
  });
