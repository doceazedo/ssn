import { error, json } from "@sveltejs/kit";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ locals }) => {
  if (!locals?.identity) throw error(404);
  return json({
    identity: locals?.identity || null
  });
};
