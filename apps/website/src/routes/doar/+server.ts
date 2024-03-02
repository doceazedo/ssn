import { redirect } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';

export const GET = () => {
	throw redirect(301, `${env.PUBLIC_IDENTITY_URL}/donate`);
};
