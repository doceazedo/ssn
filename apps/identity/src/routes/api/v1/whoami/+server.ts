import { error, json } from "@sveltejs/kit";
import { getUserByName, purifyIdentity } from "$lib/controllers/identity";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ locals }) => {
  const identity = await getUserByName('DoceAzedo');
  if (!identity) throw error(404);
  const safeIdentity = purifyIdentity(identity);

  return json({
    identity: locals?.identity || null
  });
};
