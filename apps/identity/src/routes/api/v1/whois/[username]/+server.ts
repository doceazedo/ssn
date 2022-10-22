import { error, json } from "@sveltejs/kit";
import { getUserByName, purifyIdentity } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ locals, params }) => {
  if (!params.username) throw error(400);
  const identity = await getUserByName(params.username);

  if (!identity) throw error(404);
  const safeIdentity = purifyIdentity(identity);

  return json({
    identity: safeIdentity
  });
};
