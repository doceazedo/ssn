import { SERVER_IP } from '$lib/constants';

export type ServerStatus = {
	online: boolean;
	host: string;
	port: number;
	ip_address: string;
	eula_blocked: boolean;
	retrieved_at: number;
	expires_at: number;
	srv_record: {
		host: string;
		port: number;
	};
	version?: {
		name_raw: string;
		name_clean: string;
		name_html: string;
		protocol: number;
	};
	players?: {
		online: number;
		max: number;
		list: Array<string>;
	};
	motd?: {
		raw: string;
		clean: string;
		html: string;
	};
	icon?: string;
	mods?: Array<string>;
	software?: string | null;
	plugins?: Array<string>;
};

export const getServerStatus = async (ip = SERVER_IP) => {
	try {
		const resp = await fetch(`https://api.mcstatus.io/v2/status/java/${ip}`);
		if (!resp.ok) return null;
		const data = await resp.json();
		return data as ServerStatus;
	} catch (_error) {
		return null;
	}
};
