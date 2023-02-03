import { getUser, prisma } from ".";

export const getProfile = async (uuid: string) =>
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

export const getProfileByUsername = async (name: string) => {
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