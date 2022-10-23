import { getInvite } from "warehouse";
import { registerEnabled, inviteOnly } from "$lib/env/public";
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ url }) => {
  const inviteCode = url.searchParams.get('invite');

  if (!registerEnabled || !inviteOnly) return {
    inviteCode
  };

  const invite = inviteCode ? await getInvite(inviteCode) : null;
  return {
    inviteCode,
    invite
  }
}