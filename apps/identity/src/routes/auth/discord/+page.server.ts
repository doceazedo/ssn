import { getConnection, getUser } from "warehouse";
import { dashboardUrl, discordClientId, identityUrl } from "$lib/env/public";
import { discordClientSecret } from "$lib/env/private";
import { error, redirect } from "@sveltejs/kit";
import type { PageServerLoad } from './$types';
import { setAuthCookies } from "../../../lib/utils";

export const load: PageServerLoad = async ({ url, cookies }) => {
  const code = url.searchParams.get('code');
  const redirectUrl = url.searchParams.get('redirect') || dashboardUrl;
  if (!code) throw error(400);

  // TODO: organize discord/oauth related code elsewhere
  const body = new URLSearchParams();
  body.append('client_id', discordClientId);
  body.append('client_secret', discordClientSecret);
  body.append('grant_type', 'authorization_code');
  body.append('code', code);
  body.append('redirect_uri', `${identityUrl}/auth/discord`);

  const resp = await fetch("https://discord.com/api/oauth2/token", {
    method: 'POST',
    body,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
  });
  const data = await resp.json();
  if (!resp.ok) throw error(400, 'Invalid code'); // TODO: redirect to register

  const respTEMP = await fetch('https://discord.com/api/oauth2/@me', {
    headers: {
      Authorization: `${data.token_type} ${data.access_token}`
    }
  });
  const dataTEMP = await respTEMP.json();
  if (!respTEMP.ok) throw error(400, 'Invalid access token'); // TODO: redirect to register

  // if connection already exists, login user
  const discordConnection = await getConnection(dataTEMP.user.id);
  if (discordConnection) {
    const identity = await getUser(discordConnection.ownerId);
    if (identity) {
      setAuthCookies(cookies, identity.token);
      throw redirect(302, redirectUrl);
    }
  }

  return {
    authorization: {
      ...data,
      expiresAt: dataTEMP.expires
    },
    user: dataTEMP.user
  }
}