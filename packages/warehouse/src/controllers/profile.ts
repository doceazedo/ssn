import { Profile } from "@prisma/client";
import { getUser, prisma } from ".";

export type ProfileWithLikeCount = Profile & {
  _count: {
      likes: number;
  };
}

export const getProfile = async (uuid: string): Promise<ProfileWithLikeCount | null> =>
  await prisma.profile.findUnique({
    where: {
      uuid
    },
    include: {
      _count: {
        select: {
          likes: true
        }
      }
    }
  });

export const getProfileByUsername = async (name: string): Promise<ProfileWithLikeCount | null> => {
  const username = await prisma.username.findUnique({
    where: {
      name
    }
  });
  if (!username) return null;

  return await prisma.profile.upsert({
    where: {
      ownerName: username.name
    },
    create: {
      ownerName: username.name
    },
    update: {
      
    },
    include: {
      _count: {
        select: {
          likes: true
        }
      }
    }
  });
}

// not used because it doesnt upsert
export const getUserProfiles = async (ownerId: string) => {
  const owner = await getUser(ownerId);
  if (!owner) return [];

  return await prisma.profile.findMany({
    where: {
      OR: owner.usernames.map(user => ({ ownerName: user.name }))
    }
  });
}

export const getProfileLike = async (likerId: string, profileId: string) =>
  await prisma.profileLike.findFirst({
    where: {
      likerId,
      profileId
    }
  });

export const toggleProfileLike = async (likerId: string, profileId: string) => {
  const liker = await getUser(likerId);
  if (!liker) return null;

  const profileLike = await getProfileLike(likerId, profileId);
  if (!!profileLike) {
    return await prisma.profileLike.delete({
      where: {
        id: profileLike.id
      }
    })
  }

  return await prisma.profileLike.create({
    data: {
      likerId,
      profileId
    }
  })
}

export const updateProfile = async (uuid: string, data: Partial<Profile>) =>
  await prisma.profile.update({
    where: {
      uuid
    },
    data
  });
