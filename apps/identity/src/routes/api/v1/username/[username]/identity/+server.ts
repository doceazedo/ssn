import { error, json } from "@sveltejs/kit";
import { getUserByName, purifyIdentity } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';
import { tokenOnly } from "$lib/middlewares";

export const GET: RequestHandler = async ({ params, request }) =>
  tokenOnly(request, async () => {
    if (!params.username) throw error(400);
    const identity = await getUserByName(params.username);

    if (!identity) throw error(404);
    const safeIdentity = purifyIdentity(identity);

    return json({
      identity: safeIdentity
    });
  })
