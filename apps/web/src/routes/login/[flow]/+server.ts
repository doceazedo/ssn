import { json } from '@sveltejs/kit';
import { redirect } from '@sveltejs/kit';

export const GET = async ({ locals, params }) => {
	if (!locals.user) {
		throw redirect(302, `/login?redirect=${encodeURIComponent(`/authorize?flow=${params.flow}`)}`);
	}

	redirect(302, `/authorize?flow=${params.flow}`);

	return json({ params });
};
