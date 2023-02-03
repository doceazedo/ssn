import { getProfileByUsername, getProfileLike, getUserBadges, getUserByName, getUserConnections, getUsername } from 'warehouse';
import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params, locals }) => {
  const { username } = params;
  const { identity } = locals;

  if (!username) throw error(404);

  const user = await getUsername(username);
  if (!user) throw error(404);

  const badges = await getUserBadges(user.name);
  const badgeDetails = badges.map(badge => badge.badge);

  const owner = await getUserByName(username);
  if (!owner) throw error(404);

  const profile = await getProfileByUsername(user.name);
  if (!profile) throw error(404);

  let hasLiked = false;
  let isOwner = false;
  if (identity) {
    const profileLike = await getProfileLike(identity.uuid, profile.uuid);
    hasLiked = !!profileLike;
    isOwner = identity.usernames.includes(user.name);
  }

  const alts = profile.showAlts 
    ? owner.usernames
        .map(username => username.name)
        .filter(username => username != user.name)
    : [];

  // TODO: get get actual relevant data from socials
  const connections = await getUserConnections(owner.uuid, true);
  const socials = connections.map(connection => connection.service);

  return {
    profileId: profile.uuid,
    user,
    badges: badgeDetails,
    alts,
    socials,
    aboutMe: profile.aboutMe,
    likes: profile._count.likes,
    hasLiked,
    isOwner
  };
}
