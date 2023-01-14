import { error, json } from "@sveltejs/kit";
import { purifyIdentity, updateUser } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';
import { loggedInOnly } from "$lib/middlewares";

export const POST: RequestHandler = async ({ params, locals, cookies }) =>
  loggedInOnly(locals, cookies, async (identity) => {
    if (!params.username) throw error(400);
    if (identity.primaryUsername === params.username) throw error(400);

    const newPrimaryUsername = identity.usernames.find(username => username.name === params.username);
    if (!newPrimaryUsername) throw error(403);

    const updatedUser = await updateUser(identity.uuid, {
      primaryUsername: newPrimaryUsername.name
    });
    if (!updatedUser) throw error(500);

    const safeIdentity = purifyIdentity(updatedUser);

    return json({
      identity: safeIdentity
    });
  })
