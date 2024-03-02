import {
	getDonatorStatus,
	getProfileByUsername,
	getProfileLike,
	getUserBadges,
	getUserByName,
	getUsername
} from 'warehouse';
import { error, redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params, locals }) => {
	const { username } = params;
	const { identity } = locals;

	if (!username) throw redirect(302, '/u/DoceAzedo');

	if (username === '@me') throw redirect(302, `/u/${identity?.primaryUsername || 'DoceAzedo'}`);

	const user = await getUsername(username);
	if (!user) throw error(404);

	const badges = await getUserBadges(user.name);
	const badgeDetails = badges.map((badge) => badge.badge);

	const owner = await getUserByName(username);
	if (!owner) throw error(404);

	const profile = await getProfileByUsername(user.name);
	if (!profile) throw error(404);

	const isDonator = await getDonatorStatus(user.name);

	let hasLiked = false;
	let isOwner = false;
	if (identity) {
		const profileLike = await getProfileLike(identity.uuid, profile.uuid);
		hasLiked = !!profileLike;
		isOwner = identity.usernames.includes(user.name);
	}

	const alts = profile.showAlts
		? owner.usernames.map((username) => username.name).filter((username) => username != user.name)
		: [];

	return {
		profileId: profile.uuid,
		user,
		badges: badgeDetails,
		alts,
		aboutMe: profile.aboutMe,
		likes: profile._count.likes,
		hasLiked,
		isOwner,
		isOnline: profile.showOnline && user.isOnline,
		isDonator
	};
};
