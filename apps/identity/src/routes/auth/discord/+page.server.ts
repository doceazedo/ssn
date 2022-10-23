import { getConnection, getUser } from "warehouse";
import { error, redirect } from "@sveltejs/kit";
import { setAuthCookies } from "$lib/utils";
import { exchangeTokens, getCurrentAuthorization } from "$lib/services/discord";
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ url, cookies }) => {
  const code = url.searchParams.get('code');
  if (!code) throw error(400);

  const tokens = await exchangeTokens(code);
  if (!tokens) throw redirect(302, '/auth/register');

  const authorization = await getCurrentAuthorization(tokens.access_token);
  if (!authorization) throw redirect(302, '/auth/register');

  // if connection already exists, login user
  let identity = null;
  const discordConnection = await getConnection(authorization.user.id);
  if (discordConnection) {
    identity = await getUser(discordConnection.ownerId);
    if (identity) setAuthCookies(cookies, identity.token);
  }

  return {
    identity,
    refreshToken: tokens.refresh_token,
    user: authorization.user
  }
}