import { getPublicDiscordProfile } from '$lib/services/discord';
import type { ServiceProfile } from 'ssnkit/utils';
import type { Connection } from '@prisma/client';

export const getServiceProfile = async (connection: Connection): Promise<ServiceProfile | null> => {
	switch (connection.service) {
		case 'DISCORD':
			return getPublicDiscordProfile(connection);

		default:
			return null;
	}
};
