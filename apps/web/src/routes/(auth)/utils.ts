import { goto } from '$app/navigation';
import { pb } from '$lib/pocketbase';
import { AUTH_DATA } from '$lib/stores';

export const loginWithDiscord = async () => {
	const authData = await pb.collection('users').authWithOAuth2({ provider: 'discord' });
	if (!authData.record.primaryUsername) {
		AUTH_DATA.set(authData);
		goto('/create-username');
		return;
	}
	goto('/account', { invalidateAll: true });
};
