import type { PageServerLoad } from './$types';
import { getUsernames } from "warehouse";

export const load: PageServerLoad = async ({ locals }) => {
  const { identity } = locals;
  if (!identity) return {};
  const usernames = await getUsernames(identity.uuid);
  const primaryIndex = usernames.findIndex(username => username.name === identity.primaryUsername);
  const primaryUsername = usernames.splice(primaryIndex, 1);
  return {
    usernames: [...primaryUsername, ...usernames]
  };
}
