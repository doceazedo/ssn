import geoip from 'geoip-lite';
import { error, redirect } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';
import { getFlowByCode } from '$lib/controllers/flow';
import { isValidCode } from '$lib/utils';
import { errorMessage } from '$lib/enums';
import type { PageServerLoad } from './$types';

const identityBaseUrl = env.PUBLIC_IDENTITY_URL;

export const load: PageServerLoad = async ({ params, locals }) => {
	const { code } = params;
	const { identity } = locals;

	if (!isValidCode(code)) throw error(406, errorMessage.INVALID_CODE);

	if (!identity) throw redirect(302, `${identityBaseUrl}/auth/login?gk=${code}`);

	const flow = await getFlowByCode(code);
	if (!flow) throw error(410, errorMessage.EXPIRED_CODE);

	if (flow.grantKey) throw error(409, errorMessage.GRANTED_FLOW);

	if (!identity.usernames.includes(flow.username))
		throw error(
			403,
			`Você está logado como <b>${identity.primaryUsername}</b> no site, mas está tentando jogar com o usuário <b>${flow.username}</b> no Minecraft. Tente trocar de conta ou confira suas alts no seu painel.`
		);

	const location = geoip.lookup(flow.ip);

	return {
		code,
		identity,
		username: flow.username,
		location
	};
};
