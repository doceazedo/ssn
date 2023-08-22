import { status as getServerStatus, type JavaStatusResponse } from 'minecraft-server-util';
import { json } from '@sveltejs/kit';
import type { RequestHandler } from '@sveltejs/kit';

const ip = 'ssn.gg';
const cooldown = 300000; // 5 minutes

let status: JavaStatusResponse | false | null = null;
let lastTimeFetched = new Date();

export const GET: RequestHandler = async () => {
	// only fetch for status if last time fetch was >= 5 minutes ago
	const now = Date.now();
	if (status == null || now - lastTimeFetched.getTime() >= cooldown) {
		try {
			status = await getServerStatus(ip);
			lastTimeFetched = new Date();
		} catch (e) {
			status = false;
		}
	}

	return json({
		status
	});
};
