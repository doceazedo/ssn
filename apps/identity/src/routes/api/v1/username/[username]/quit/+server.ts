import { error, json } from "@sveltejs/kit";
import { getUsername, updateUsername } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';
import { tokenOnly } from "$lib/middlewares";

export const POST: RequestHandler = async ({ params, request }) =>
  tokenOnly(request, async () => {
    if (!params.username) throw error(400);

    const username = await getUsername(params.username);
    if (!username) throw error(404);

    const now = new Date();
    const lastSeen = username.lastSeen || now;
    const sessionDuration = parseInt((now.getTime() - lastSeen.getTime()) / 1000);

    const updatedUsername = await updateUsername(username.name, {
      lastSeen: now,
      playedSeconds: username.playedSeconds + sessionDuration,
      isOnline: false
    })

    return json({
      username: updatedUsername
    });
  })
