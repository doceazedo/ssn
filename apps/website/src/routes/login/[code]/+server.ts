import { redirect } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';

export const GET = ({ params }) => {
	throw redirect(302, `${env.PUBLIC_GATEKEEPER_URL}/${params.code}`);
};
