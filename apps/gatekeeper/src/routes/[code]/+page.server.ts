import geoip from 'geoip-lite';
import { error, redirect } from "@sveltejs/kit";
import { env } from '$env/dynamic/public';
import { getFlowByCode } from "$lib/controllers/flow";
import { isValidCode } from '$lib/utils';
import { errorMessage } from '$lib/enums';
import type { PageServerLoad } from './$types';

const identityBaseUrl = env.PUBLIC_IDENTITY_URL;

export const load: PageServerLoad = async ({ params, locals, url }) => {
  const { code } = params;
  const { identity } = locals;

  if (!isValidCode(code))
    throw error(406, errorMessage.INVALID_CODE);

  // FIXME: remove this later
  const DEBUG = url.href.replace('https://', 'http://');

  if (!identity)
    throw redirect(302, `${identityBaseUrl}/auth/login?redirect=${DEBUG}`);

  const flow = await getFlowByCode(code);
  if (!flow)
    throw error(410, errorMessage.EXPIRED_CODE);

  if (flow.grantKey)
    throw error(409, errorMessage.GRANTED_FLOW);

  if (!identity.usernames.includes(flow.username))
    throw error(403, errorMessage.FORBIDDEN);

  const loc = geoip.lookup(flow.ip);

  return {
    code,
    identity,
    username: "DoceAzedo",
    location: {
      city: loc?.city,
      region: loc?.region,
      country: loc?.country,
    },
  };
}
