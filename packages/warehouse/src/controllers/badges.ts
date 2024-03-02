import { Badge } from "@prisma/client";
import { prisma } from ".";

const getBadge = async (slug: string) =>
  await prisma.badge.findUnique({
    where: {
      slug,
    },
  });

const createBadge = async (slug: string, label: string) =>
  await prisma.badge.create({
    data: {
      slug,
      label,
    },
  });

export const giveDonationBadge = async (user: string) => {
  const slug = "donator";
  const badge = await getBadge(slug);
  if (!badge) await createBadge(slug, "Eu doei para o SSN");
  try {
    return await prisma.usernameBadges.create({
      data: {
        ownerName: user,
        badgeSlug: slug,
      },
    });
  } catch (error) {
    return null;
  }
};
