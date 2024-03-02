import { env } from '$env/dynamic/private';

const baseUrl = `${env.LOCAL_COMMANDER_URL}/api`;

export const giveRank = async (player: string, rank: string, duration: string) => {
	try {
		const resp = await fetch(`${baseUrl}/private/give-rank`, {
			method: 'POST',
			body: JSON.stringify({
				player,
				rank,
				duration
			}),
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${env.COMMANDER_TOKEN}`
			}
		});
		return resp.ok;
	} catch (error) {
		return false;
	}
};

export const broadcast = async (message: string | string[], sound = false) => {
	try {
		const messages = Array.isArray(message) ? message : [message];
		const resp = await fetch(`${baseUrl}/private/broadcast`, {
			method: 'POST',
			body: JSON.stringify({
				messages,
				sound
			}),
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${env.COMMANDER_TOKEN}`
			}
		});
		console.log(resp);
		console.log({ ok: resp.ok });
		return resp.ok;
	} catch (error) {
		return false;
	}
};
