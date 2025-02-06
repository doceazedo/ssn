import { goto } from '$app/navigation';
import { i18n } from '$lib/i18n';
import { pb } from '$lib/pocketbase';
import { AUTH_DATA } from '$lib/stores';

export const loginWithDiscord = async (search = '') => {
	const authData = await pb.collection('users').authWithOAuth2({ provider: 'discord' });
	if (!authData.record.primaryUsername) {
		AUTH_DATA.set(authData);
		goto(i18n.resolveRoute(`/create-username${search}`));
		return;
	}

	const searchParams = new URLSearchParams(search);
	const url = searchParams.get('redirect') || '/account';
	goto(i18n.resolveRoute(url), { invalidateAll: true });
};
