import { discordClientId, identityUrl } from "$lib/env/public";
import { discordClientSecret } from "$lib/env/private";

type ExchangeTokensResponse = {
  access_token: string,
  token_type: string,
  expires_in: number,
  refresh_token: string,
  scope: string
}

type CurrentAuthorizationResponse = {
  application: {
    id: string,
    name: string,
    icon: string,
    description: string,
    hook: boolean,
    bot_public: boolean,
    bot_require_code_grant: boolean,
    verify_key: string
  },
  scopes: string[],
  expires: Date,
  user: {
    id: string,
    username: string,
    avatar: string,
    discriminator: string,
    public_flags: number
  }
}

const discordBaseUrl = 'https://discord.com/api';

export const exchangeTokens = async (code: string): Promise<ExchangeTokensResponse | null> => {
  const body = new URLSearchParams();
  body.append('client_id', discordClientId);
  body.append('client_secret', discordClientSecret);
  body.append('grant_type', 'authorization_code');
  body.append('code', code);
  body.append('redirect_uri', `${identityUrl}/auth/discord`);

  try {
    const resp = await fetch(`${discordBaseUrl}/oauth2/token`, {
      method: 'POST',
      body,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    });
    const data = await resp.json();
    return !data.error ? data : null
  } catch (e) {
    return null;
  }
}

export const refreshAccessToken = async (refreshToken: string): Promise<ExchangeTokensResponse | null> => {
  const body = new URLSearchParams();
  body.append('client_id', discordClientId);
  body.append('client_secret', discordClientSecret);
  body.append('grant_type', 'refresh_token');
  body.append('refresh_token', refreshToken);

  try {
    const resp = await fetch(`${discordBaseUrl}/oauth2/token`, {
      method: 'POST',
      body,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    });
    const data = await resp.json();
    return !data.error ? data : null
  } catch (e) {
    return null;
  }
}

export const getCurrentAuthorization = async (accessToken: string): Promise<CurrentAuthorizationResponse | null> => {
  try {
    const resp = await fetch(`${discordBaseUrl}/oauth2/@me`, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    });
    const data = await resp.json();
    return !data.error ? data : null
  } catch (e) {
    return null;
  }
}