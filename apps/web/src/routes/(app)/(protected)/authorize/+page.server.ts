import { getRedisClient } from '$lib/redis';
import { error, redirect, type Actions } from '@sveltejs/kit';
import * as m from '$lib/paraglide/messages.js';

type FlowData = {
	username: string;
	ip: string;
	grantKey?: string;
};

type Geolocation = {
	country_code: string;
	country_code3: string;
	continent_code: string;
	region: string;
	latitude: string;
	longitude: string;
	accuracy: number;
	ip: string;
	timezone: string;
	organization_name: string;
	organization: string;
	city: string;
	country: string;
	asn: number;
	area_code: string;
};

export const load = async ({ url, locals }) => {
	if (!locals.user) {
		return redirect(302, '/login');
	}

	const flowCode = url.searchParams.get('flow');
	if (!flowCode) {
		return error(400, m.authorize_login_no_code_error());
	}

	const isValidCode = validateCode(flowCode);
	if (!isValidCode) {
		return error(400, m.authorize_login_invalid_code_error());
	}

	const flow = await getFlow(flowCode);
	if (!flow) {
		return error(410, m.authorize_login_flow_not_found_error());
	}

	try {
		await locals.pb
			.collection('usernames')
			.getFirstListItem(`name="${flow.username}" && owner="${locals.user.id}"`);
	} catch (_error) {
		return error(403, m.authorize_login_prohibited());
	}

	const geolocation = await getGeolocation(flow.ip);

	return {
		username: flow.username,
		region: geolocation
			? `${geolocation.city}, ${geolocation.region}, ${geolocation.country_code}`
			: m.unknown_region()
	};
};

export const actions = {
	allow: async ({ locals, request }) => {
		if (!locals.user) {
			return { success: false };
		}

		const data = await request.formData();
		const flowCode = data.get('flowCode')?.toString() || '';
		const alwaysAllowIp = data.get('alwaysAllowIp') === 'on';
		const allowWildcard = data.get('allowWildcard') === 'on';

		console.log({
			flowCode,
			alwaysAllowIp,
			allowWildcard
		});

		const flow = await getFlow(flowCode);
		if (!flow) {
			return { success: false };
		}

		try {
			await locals.pb
				.collection('usernames')
				.getFirstListItem(`name="${flow.username}" && owner="${locals.user.id}"`);
		} catch (_error) {
			return { success: false };
		}

		const grantKey = await setGrant(
			locals.user.id,
			flow.ip,
			allowWildcard ? '*' : flow.username,
			alwaysAllowIp ? -1 : 10
		);
		if (!grantKey) {
			return { success: false };
		}

		const updatedFlow = await setFlow(flowCode, {
			...flow,
			grantKey
		});

		return { success: updatedFlow };
	}
} satisfies Actions;

const getFlow = async (code: string) => {
	const redis = await getRedisClient();
	const flow = await redis.get(`flows:${code}`);
	if (!flow) return null;

	const flowData = JSON.parse(flow);
	return flowData as FlowData;
};

const getGeolocation = async (ip: string) => {
	try {
		const resp = await fetch(`https://get.geojs.io/v1/ip/geo/${ip}.json`);
		if (!resp.ok) return null;
		const data = await resp.json();
		return data as Geolocation;
	} catch (_error) {
		return null;
	}
};

const validateCode = (code: string) => /^[a-zA-Z0-9]{5}$/.test(code);

const setGrant = async (
	ownerId: string,
	ip: string,
	username: '*' | string,
	ttl: number,
	authorize = true
) => {
	const redis = await getRedisClient();
	const key = `grants:${ownerId}:${ip}:${username.toLowerCase()}`;
	const value = authorize.toString();
	try {
		const result = ttl >= 0 ? await redis.setEx(key, ttl, value) : await redis.set(key, value);
		if (result !== 'OK') return null;
	} catch (error) {
		console.log(error);
		return null;
	}
	return key;
};

const setFlow = async (code: string, data: FlowData) => {
	const redis = await getRedisClient();
	const result = await redis.setEx(`flows:${code}`, 10, JSON.stringify(data));
	if (result !== 'OK') return false;
	return true;
};
