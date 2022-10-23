import { error, json } from "@sveltejs/kit";
import { getUsername } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ locals, params }) => {
  if (!params.username) throw error(400);
  if (!locals?.identity) throw error(401);
  if (!locals.identity.usernames.includes(params.username)) throw error(403);

  const username = await getUsername(params.username);
  if (!username) throw error(404);

  return json({
    username
  });
};
