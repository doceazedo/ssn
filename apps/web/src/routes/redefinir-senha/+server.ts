import { redirect } from '@sveltejs/kit';

export const GET = () => {
	return redirect(302, '/password-reset');
};
