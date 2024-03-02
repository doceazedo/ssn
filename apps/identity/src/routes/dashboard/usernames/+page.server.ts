import type { PageServerLoad } from './$types';
import { filterDonators, getUsernames } from 'warehouse';

export const load: PageServerLoad = async ({ locals }) => {
	const { identity } = locals;
	if (!identity)
		return {
			usernames: [],
			donators: []
		};
	const usernames = await getUsernames(identity.uuid);
	const primaryIndex = usernames.findIndex(
		(username) => username.name === identity.primaryUsername
	);
	const donators = await filterDonators(usernames.map((x) => x.name));
	const primaryUsername = usernames.splice(primaryIndex, 1);
	return {
		usernames: [...primaryUsername, ...usernames],
		donators
	};
};
