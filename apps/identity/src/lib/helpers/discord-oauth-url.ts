import { discordClientId, identityUrl } from '$lib/env/public';

export const discordOauthUrl = (gkCode?: string | null) =>
	`https://discord.com/oauth2/authorize?client_id=${discordClientId}&redirect_uri=${encodeURI(
		`${identityUrl}/auth/discord${gkCode ? `&gk=${gkCode}` : ''}`
	)}&response_type=code&scope=identify`;
