import { env } from '$env/dynamic/private';

const baseUrl = 'https://challenges.cloudflare.com/turnstile/v0';

const verifyTurnstile = async (token: string) => {
	try {
		const resp = await fetch(`${baseUrl}/siteverify`, {
			body: `secret=${env.CF_TURNSTILE_SECRET_KEY}&response=${token}`,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			method: 'POST'
		});
		if (!resp.ok) return false;
		const data = await resp.json();
		return !!data.success;
	} catch (e) {
		return false;
	}
};

export const validateCaptcha = async (token?: string) => {
	if (!env.CF_TURNSTILE_SECRET_KEY) return true;
	if (token === env.CATRACA_IDENTITY_TOKEN) return true;
	if (!token || !token.length) return false;
	return await verifyTurnstile(token);
};
