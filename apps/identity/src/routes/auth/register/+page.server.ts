import { getInvite } from "warehouse";
import { registerEnabled, inviteOnly } from "$lib/env/public";
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ url }) => {
  if (!registerEnabled || !inviteOnly) return {};

  const inviteCode = url.searchParams.get('invite');
  const invite = inviteCode ? await getInvite(inviteCode) : null;

  return {
    inviteCode,
    invite
  }
}