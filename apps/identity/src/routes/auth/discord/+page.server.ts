import { getConnection, getUser } from "warehouse";
import { error, redirect } from "@sveltejs/kit";
import { dashboardUrl } from "$lib/env/public";
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
  const discordConnection = await getConnection(authorization.user.id);
  if (discordConnection) {
    const identity = await getUser(discordConnection.ownerId);
    if (identity) {
      setAuthCookies(cookies, identity.token);
      throw redirect(302, dashboardUrl);
    }
  }

  return {
    refreshToken: tokens.refresh_token,
    user: authorization.user
  }
}