import { error, json } from "@sveltejs/kit";
import { env } from "$env/dynamic/private";
import { purifyIdentity, updateUserRole, type SafeIdentity } from 'warehouse';
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ locals }) => {
  if (!locals?.identity) throw error(404);
  let identity: SafeIdentity | null = locals.identity;

  // Force set user to ADMIN if they are the default game master
  const defaultGM = env.WH_DEFAULT_ADMIN_USERNAME;
  if (defaultGM && locals.identity.primaryUsername === defaultGM && locals.identity.role !== 'ADMIN') {
    const user = await updateUserRole(identity.uuid, 'ADMIN');
    if (user) identity = purifyIdentity(user);
  }

  return json({
    identity
  });
};
