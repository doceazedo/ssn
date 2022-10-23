import { env } from '$env/dynamic/public';

export const baseDomain = `${env.PUBLIC_BASE_DOMAIN}`;
export const dashboardUrl = `${env.PUBLIC_DASHBOARD_URL}`;
export const identityUrl = `${env.PUBLIC_IDENTITY_URL}`;

export const registerEnabled = env.PUBLIC_REGISTER_ENABLED === 'true';
export const inviteOnly = env.PUBLIC_REGISTER_INVITE_ONLY === 'true';
export const invitesPerUser = parseInt(env.PUBLIC_REGISTER_INVITES_PER_USER || '') || 0;

export const discordClientId = `${env.PUBLIC_DISCORD_CLIENT_ID}`;
