import type { PageServerLoad } from './$types';
import { getUserInvites } from "warehouse";

export const load: PageServerLoad = async ({ locals }) => {
  const { identity } = locals;
  if (!identity) return {};
  const invites = await getUserInvites(identity.uuid);
  const unusedInvites = invites.filter(invite => !invite.usedById);
  const invitedUsers = invites.filter(invite => invite.usedById).map(invite => invite.usedBy?.primaryUsername);
  return {
    invites: unusedInvites,
    invitedUsers
  };
}
