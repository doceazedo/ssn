import { env } from '$env/dynamic/public';
import type { ProfileLike, Service } from '@prisma/client';
import type { ServiceProfile } from 'ssnkit/utils/connections';

export type ProfileSocials = ({
	service: Service;
} & ServiceProfile)[];

const baseUrl = '/api/v1';
const identityBaseUrl = `${env.PUBLIC_IDENTITY_URL}/api/v1`;

export const toggleProfileLike = async (profileId: string): Promise<ProfileLike | null> => {
	try {
		const resp = await fetch(`${baseUrl}/profile/${profileId}/like`, {
			method: 'POST'
		});
		const data = await resp.json();
		if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
		return data?.like || null;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};

export const getProfileSocials = async (username: string): Promise<ProfileSocials> => {
	try {
		const resp = await fetch(`${identityBaseUrl}/username/${username}/profile/socials`);
		const data = await resp.json();
		if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
		return data?.socials || [];
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};
