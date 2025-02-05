import { dev } from '$app/environment';
import { pb } from '$lib/pocketbase';

pb.authStore.loadFromCookie(document.cookie);

pb.authStore.onChange(() => {
	document.cookie = pb.authStore.exportToCookie({ httpOnly: false, secure: !dev, sameSite: 'Lax' });
}, true);
