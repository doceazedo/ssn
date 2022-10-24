import type { PageServerLoad } from './$types';
import { getUsernames } from "warehouse";

export const load: PageServerLoad = async ({ locals }) => {
  const { identity } = locals;
  if (!identity) return {};
  const usernames = await getUsernames(identity.uuid);
  return { usernames };
}
