import { error } from "@sveltejs/kit";
import { getFlowByCode, setFlow } from "$lib/controllers/flow";
import { isGranted, setGrant, validGrantTTL } from "$lib/controllers/grant";
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

  if (flow.grantKey)
    throw error(409, errorMessage.GRANTED_FLOW);

  const existingGrant = await isGranted(identity.uuid, flow.ip, flow.username);
  if (existingGrant)
    throw error(409, errorMessage.GRANTED_USERNAME);

  const grant = await setGrant({
    ownerUuid: identity.uuid,
    ip: flow.ip,
    username: allUsernames ? '*' : flow.username,
    authorized: true
  }, ttl);
  if (!grant)
    throw error(500, errorMessage.GRANT_FAIL);

  const updatedFlow = await setFlow(flow.code, {
    username: flow.username,
    ip: flow.ip,
    grantKey: grant.key
  });
  if (!updatedFlow)
    throw error(500, errorMessage.FLOW_FAIL);
}
