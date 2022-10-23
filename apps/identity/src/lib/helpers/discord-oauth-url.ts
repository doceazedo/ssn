import { discordClientId, identityUrl } from "$lib/env/public";

const discordRedirectUrl = encodeURI(`${identityUrl}/auth/discord`);

export const discordOauthUrl =
  `https://discord.com/oauth2/authorize?client_id=${discordClientId}&redirect_uri=${discordRedirectUrl}&response_type=code&scope=identify`;