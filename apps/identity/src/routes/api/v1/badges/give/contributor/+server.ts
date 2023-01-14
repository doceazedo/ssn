import { prisma } from "warehouse";
import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import type { RequestHandler } from '@sveltejs/kit';
import { validateRequest } from "$lib/middlewares";

type GiveBadgeParams = {
  username: string;
}

const slug = 'contributor';

export const POST: RequestHandler = async ({ locals, request }) =>
  validateRequest<GiveBadgeParams>(request, yup.object().shape({
    username: yup.string().required()
  }), async (body) => {
    if (!locals?.identity) throw error(401);
    if (locals.identity.role !== 'ADMIN') throw error(403);

    const badge = await prisma.badge.upsert({
      where: {
        slug
      },
      create: {
        slug,
        label: 'Contribuidor open-source'
      },
      update: {},
    });

    const user = await prisma.username.findUnique({
      where: {
        name: body.username
      }
    });
    if (!user) throw error(404, 'Username does not exist');

    const userBadge = await prisma.usernameBadges.create({
      data: {
        ownerName: user.name,
        badgeSlug: slug
      }
    })

    return json({ badge, userBadge });
  });
