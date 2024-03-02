import type { PageServerLoad } from './$types';
import { getProfileByUsername } from 'warehouse';

export const load: PageServerLoad = async ({ locals }) => {
	const { identity } = locals;
	if (!identity) return {};

	const profile = await getProfileByUsername(identity.usernames[0]);
	if (!profile) return {};

	return {
		profile
	};
};
