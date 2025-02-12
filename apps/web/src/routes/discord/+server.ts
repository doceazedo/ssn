import { DISCORD_INVITE } from '$lib/constants';
import { redirect } from '@sveltejs/kit';

export const GET = () => {
	return redirect(302, DISCORD_INVITE);
};
