import { prisma } from "warehouse";
import { generateSlug } from "random-word-slugs";
import { error, json } from "@sveltejs/kit";
import type { RequestHandler } from '@sveltejs/kit';

export const POST: RequestHandler = async ({ locals }) => {
  if (!locals?.identity) throw error(404);
  if (locals.identity.role !== 'ADMIN') throw error(401);

  const invite = await prisma.invite.create({
    data: {
      code: generateSlug(),
      ownerId: locals.identity.uuid
    }
  })

  return json({
    invite
  });
};
