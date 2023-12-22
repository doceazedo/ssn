import { env } from '$env/dynamic/private';

const baseUrl = `${env.LOCAL_COMMANDER_URL}/api/private`;

export const kickPlayer = async (username: string) => {
	try {
		const resp = await fetch(`${baseUrl}/player/${username}/kick`, {
			method: 'POST',
			headers: {
				Authorization: `Bearer ${env.COMMANDER_TOKEN}`
			}
		});
		if (!resp.ok) throw Error('Erro desconhecido');
		return true;
	} catch (e: any) {
		console.error(e);
		throw Error('Erro desconhecido');
	}
};

export const deletePlayer = async (username: string) => {
	try {
		const resp = await fetch(`${baseUrl}/player/${username}`, {
			method: 'DELETE',
			headers: {
				Authorization: `Bearer ${env.COMMANDER_TOKEN}`
			}
		});
		if (!resp.ok) throw Error('Erro desconhecido');
		return true;
	} catch (e: any) {
		console.error(e);
		throw Error('Erro desconhecido');
	}
};
