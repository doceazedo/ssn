import type { SafeIdentity } from 'warehouse';
import type { Username } from '@prisma/client';

const baseUrl = '/api/v1/username';

export const updatePrimaryUsername = async (username: string): Promise<SafeIdentity> => {
	try {
		const resp = await fetch(`${baseUrl}/${username}/primary`, {
			method: 'POST'
		});
		const data = await resp.json();
		if (!resp.ok || !data.identity) throw Error(data.message || 'Erro desconhecido');
		return data.identity;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};

export const deleteUsername = async (
	username: string,
	deleteIngameData = false
): Promise<Username> => {
	try {
		const resp = await fetch(`${baseUrl}/${username}`, {
			method: 'DELETE',
			body: JSON.stringify({
				deleteIngameData
			})
		});
		const data = await resp.json();
		if (!resp.ok || !data.username) throw Error(data.message || 'Erro desconhecido');
		return data.username;
	} catch (e: any) {
		console.error(e);
		throw Error(e.message || 'Erro desconhecido');
	}
};
