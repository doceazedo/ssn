import { getUserBadges, getUsername } from 'warehouse';
import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
  const { username } = params;
  if (!username) throw error(404);

  const user = await getUsername(username);
  if (!user) throw error(404);

  const badges = await getUserBadges(user.name);
  const badgeDetails = badges.map(badge => badge.badge);

  return {
    user,
    badges: badgeDetails
  };
}
