import { error } from "@sveltejs/kit";
import { getFlowByCode } from "$lib/controllers/flow";
import { validGrantTTL } from '$lib/controllers/grant';
import { isValidCode } from '$lib/utils';
import { errorMessage } from '$lib/enums';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ locals, url }) => {
  const { identity } = locals;

  const code = url.searchParams.get('code') || '';
  const ttl = parseInt(url.searchParams.get('ttl') || '');
  const allUsernames = url.searchParams.get('grantAlts') === 'true';

  if (!isValidCode(code))
    throw error(403, errorMessage.INVALID_CODE);

  if (!validGrantTTL.includes(ttl))
    throw error(400, errorMessage.INVALID_TTL);

  if (!identity)
    throw error(401, errorMessage.UNAUTHORIZED);

  const flow = await getFlowByCode(code);
  if (!flow)
    throw error(410, errorMessage.EXPIRED_CODE);

  if (flow.grantUuid)
    throw error(409, errorMessage.GRANTED_FLOW);

  // TODO: check if there is a grant for this user/owner/ip already

  // TODO: createGrant, updateFlow with grantUuid

  return {
    code,
    ttl,
    allUsernames
  };
}
