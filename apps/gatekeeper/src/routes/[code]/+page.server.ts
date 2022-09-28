import geoip from 'geoip-lite';
import { error, redirect } from "@sveltejs/kit";
import { env } from '$env/dynamic/public';
import { isValidCode } from '$lib/utils';
import type { PageServerLoad } from './$types';

const identityBaseUrl = env.PUBLIC_IDENTITY_URL;

export const load: PageServerLoad = ({ params, locals, url }) => {
  const { code } = params;
  const { identity } = locals;

  if (!isValidCode(code)) throw error(406);

  if (!identity)
    return redirect(302, `${identityBaseUrl}/auth/login?redirect=${url.href}`);

  // TODO: get flow by code (if not found, return 410)

  // TODO: check if identity is owner of nickname (if not, return 403)

  const loc = geoip.lookup('187.84.244.135');

  return {
    code,
    identity,
    nickname: "DoceAzedo",
    location: {
      city: loc?.city,
      region: loc?.region,
      country: loc?.country,
    },
  };
}
