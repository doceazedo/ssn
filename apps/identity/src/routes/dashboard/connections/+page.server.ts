import { getUserConnections } from 'warehouse';
import { getServiceProfile } from '$lib/utils';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ locals }) => {
	const { identity } = locals;
	if (!identity) return {};

	const connections = await getUserConnections(identity.uuid);

	return {
		services: await Promise.all(
			connections.map(async (connection) => ({
				service: connection.service,
				isPublic: connection.isPublic,
				profile: await getServiceProfile(connection)
			}))
		)
	};
};
