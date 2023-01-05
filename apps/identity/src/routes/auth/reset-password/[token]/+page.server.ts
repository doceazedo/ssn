import { getUserByPasswordResetToken, purifyIdentity } from "warehouse";
import { error } from "@sveltejs/kit";
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params }) => {
  if (!params.token) throw error(400);

  const identity = await getUserByPasswordResetToken(params.token);
  if (!identity) throw error(410);

  const safeIdentity = purifyIdentity(identity);
  return {
    identity: safeIdentity,
    token: params.token
  }
}
