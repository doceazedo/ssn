import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import { getProfileByUsername, updateProfile, type IdentityWithUsernames } from "warehouse";
import type { RequestHandler } from '@sveltejs/kit';
import { loggedInOnly, validateRequest } from "$lib/middlewares";

export type UpdateProfileParams = {
  aboutMe?: string;
  showAlts?: boolean;
}

const getOwningUsername = (name: string | undefined, identity: IdentityWithUsernames) => {
  if (!name) throw error(400);
  const username = identity.usernames.find(user => user.name == name);
  if (!username) throw error(403);
  return username;
}

export const GET: RequestHandler = async ({ params, locals, cookies }) =>
  loggedInOnly(locals, cookies, async (identity) => {
    const username = getOwningUsername(params.username, identity);
    const profile = await getProfileByUsername(username.name);

    return json({
      profile
    });
  })

export const PATCH: RequestHandler = async ({ params, request, locals, cookies }) =>
  validateRequest<UpdateProfileParams>(request, yup.object().shape({
    aboutMe: yup.string().max(2000, 'Sobre mim deve ter no máximo 2000 caracteres').nullable(),
    showAlts: yup.boolean().nullable(),
  }), async (body) =>
    loggedInOnly(locals, cookies, async (identity) => {
      const username = getOwningUsername(params.username, identity);

      const profile = await getProfileByUsername(username.name);
      if (!profile) throw error(500, 'Could not retrieve profile');

      const updatedProfile = updateProfile(profile.uuid, body);

      return json({
        profile: updatedProfile
      });
    })
  );