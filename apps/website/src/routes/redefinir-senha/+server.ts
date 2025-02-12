import { redirect } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';

export const GET = () => {
	throw redirect(302, `${env.PUBLIC_IDENTITY_URL}/auth/reset-password`);
};
