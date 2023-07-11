import type { ProfileWithLikeCount } from 'warehouse';
import type { UpdateProfileParams } from '../../routes/api/v1/username/[username]/profile/+server';
import type { PublicSocialsParams } from '../../routes/api/v1/profile/socials/+server';

const baseUrl = '/api/v1';

export const getProfileByUsername = async (username: string): Promise<ProfileWithLikeCount> => {
	try {
		const resp = await fetch(`${baseUrl}/username/${username}/profile`);
		const data = await resp.json();
		if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
		if (!data.profile) throw Error('Nenhum perfil retornado');
		return data.profile;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};

export const getProfileSocials = async (username: string): Promise<ProfileWithLikeCount> => {
	try {
		const resp = await fetch(`${baseUrl}/username/${username}/profile/socials`);
		const data = await resp.json();
		if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
		if (!data.profile) throw Error('Nenhum perfil retornado');
		return data.profile;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};

export const updateProfile = async (username: string, body: UpdateProfileParams) => {
	try {
		const resp = await fetch(`${baseUrl}/username/${username}/profile`, {
			method: 'PATCH',
			body: JSON.stringify(body)
		});
		const data = await resp.json();
		if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
		if (!data.profile) throw Error('Nenhum perfil retornado');
		return data.profile;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};

export const updatePublicConnection = async (body: PublicSocialsParams) => {
	try {
		const resp = await fetch(`${baseUrl}/profile/socials`, {
			method: 'PATCH',
			body: JSON.stringify(body)
		});
		const data = await resp.json();
		if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
		return true;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};
