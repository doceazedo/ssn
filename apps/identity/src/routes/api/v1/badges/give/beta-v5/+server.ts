import { prisma } from "warehouse";
import { error, json } from "@sveltejs/kit";
import type { RequestHandler } from '@sveltejs/kit';

const slug = 'beta-v5';

export const POST: RequestHandler = async ({ locals }) => {
  if (!locals?.identity) throw error(401);
  if (locals.identity.role !== 'ADMIN') throw error(403);

  const badge = await prisma.badge.upsert({
    where: {
      slug
    },
    create: {
      slug,
      label: 'Beta tester do SSN 5'
    },
    update: {},
  });

  const playedBeforeUsers = await prisma.username.findMany({
    where: {
      NOT: [
        {
          firstJoin: null
        }
      ]
    }
  });

  const badges = await prisma.usernameBadges.createMany({
    data: playedBeforeUsers.map(user => ({
      ownerName: user.name,
      badgeSlug: slug
    }))
  })

  return json({ badge, badges });
};
