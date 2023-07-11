import { env } from '$env/dynamic/private';
import { error, json } from '@sveltejs/kit';
import type { RequestHandler } from '@sveltejs/kit';

const baseUrl = `${env.LOCAL_IDENTITY_URL}/api/v1`;

export const GET: RequestHandler = async ({ params }) => {
	try {
		const resp = await fetch(`${baseUrl}/username/${params.username}/profile/socials`);
		const data = await resp.json();
		return json(data);
	} catch (e) {
		console.error(e);
		throw error(500);
	}
};
