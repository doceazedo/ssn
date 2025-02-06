import { i18n } from '$lib/i18n';
import { sequence } from '@sveltejs/kit/hooks';
import PocketBase from 'pocketbase';
import { env } from '$env/dynamic/private';

export const handle = sequence(i18n.handle(), async ({ event, resolve }) => {
	const pb = new PocketBase(env.LOCAL_POCKETBASE_URL);
	pb.authStore.loadFromCookie(event.request.headers.get('cookie') || '');

	try {
		if (pb.authStore.isValid) {
			await pb.collection('users').authRefresh();
		}
	} catch (_error) {
		pb.authStore.clear();
	}

	event.locals.pb = pb;
	event.locals.user = pb.authStore.record;

	const response = await resolve(event);
	response.headers.set(
		'set-cookie',
		pb.authStore.exportToCookie({ httpOnly: false, sameSite: 'Lax' })
	);

	return response;
});
