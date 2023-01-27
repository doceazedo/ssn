import { getUserBadges, getUserByName, getUserConnections, getUsername } from 'warehouse';
import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
  const { username } = params;
  if (!username) throw error(404);

  const user = await getUsername(username);
  if (!user) throw error(404);

  const badges = await getUserBadges(user.name);
  const badgeDetails = badges.map(badge => badge.badge);

  const identity = await getUserByName(username);
  if (!identity) throw error(404);

  // TODO: check if profile allows showing alts
  const alts = identity.usernames
    .map(username => username.name)
    .filter(username => username != user.name);

  // TODO: get socials from profile (and get actual relevant data)
  const connections = await getUserConnections(identity.uuid);
  const socials = connections.map(connection => connection.service);

  return {
    user,
    badges: badgeDetails,
    alts,
    socials
  };
}
