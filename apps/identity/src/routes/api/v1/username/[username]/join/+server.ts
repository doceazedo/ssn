import { error, json } from "@sveltejs/kit";
import { getUsername, updateUsername } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';
import { tokenOnly } from "$lib/middlewares";

export const POST: RequestHandler = async ({ params, request }) =>
  tokenOnly(request, async () => {
    if (!params.username) throw error(400);

    const username = await getUsername(params.username);
    if (!username) throw error(404);

    const updatedUsername = await updateUsername(username.name, {
      firstJoin: !username.lastSeen ? new Date() : undefined,
      lastSeen: new Date(),
      isOnline: true
    })

    return json({
      username: updatedUsername
    });
  })
