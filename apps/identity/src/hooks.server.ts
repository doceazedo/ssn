import { getUserByToken, purifyIdentity } from "$lib/controllers/identity";
import type { Handle } from '@sveltejs/kit';

export const handle: Handle = async ({ event, resolve }) => {
  const token = event.cookies.get('token');
  if (!token) return await resolve(event);

  const identity = await getUserByToken(token);
  if (!identity) return resolve(event);

  const safeIdentity = purifyIdentity(identity);
  event.locals.identity = safeIdentity;
  return await resolve(event);
};
