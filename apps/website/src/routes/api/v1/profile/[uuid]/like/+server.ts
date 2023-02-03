import { error, json } from "@sveltejs/kit";
import { getProfile, getUserByName, toggleProfileLike } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';
import { loggedInOnly } from "$lib/middlewares";

export const POST: RequestHandler = async ({ params, locals, cookies }) =>
  loggedInOnly(locals, cookies, async (identity) => {
    if (!params.uuid) throw error(400);

    const profile = await getProfile(params.uuid);
    if (!profile) throw error(400);

    const owner = await getUserByName(profile.ownerName);
    if (!owner) throw error(400);
    if (identity.uuid == owner.uuid) throw error(400, 'Você não pode curtir seu próprio perfil.');

    const profileLike = await toggleProfileLike(identity.uuid, profile.uuid);
    return json({
      like: profileLike
    });
  })
