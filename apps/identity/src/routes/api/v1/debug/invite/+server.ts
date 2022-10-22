import { error, json } from "@sveltejs/kit";
import { generateUserInvites, getUserInvites } from "warehouse";
import { invitesPerUser } from "$lib/env/public";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ locals }) => {
  if (!locals?.identity) throw error(404);
  await generateUserInvites(locals.identity.uuid, invitesPerUser);
  const invites = await getUserInvites(locals.identity.uuid);

  return json({
    invites
  });
};
