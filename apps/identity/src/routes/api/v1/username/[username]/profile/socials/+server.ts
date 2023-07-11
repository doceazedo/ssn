import { getProfileByUsername, getUserByName, getUserConnections } from 'warehouse';
import { getServiceProfile } from '$lib/utils';
import { error, json, type RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ params, setHeaders }) => {
	if (!params.username) throw error(400);
	const user = await getUserByName(params.username);
	if (!user) throw error(404, 'User not found');

	const profile = await getProfileByUsername(params.username);
	if (!profile) throw error(500, 'Could not retrieve profile');

	const connections = profile.showSocials ? await getUserConnections(user.uuid, true) : [];
	const socials = await Promise.all(
		connections.map(async (service) => {
			const profile = await getServiceProfile(service);
			return {
				service: service.service,
				...profile
			};
		})
	);

	setHeaders({
		'Access-Control-Allow-Origin': '*'
	});

	return json({
		socials
	});
};
