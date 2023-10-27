import { dev } from '$app/environment';
import { baseDomain } from '$lib/env/public';
import type { Cookies } from '@sveltejs/kit';
import type { CookieSerializeOptions } from 'cookie';

const options: CookieSerializeOptions = {
	httpOnly: true,
	sameSite: 'strict',
	secure: false, //!dev
	domain: baseDomain,
	path: '/'
};

export const setAuthCookies = (cookies: Cookies, token: string) =>
	cookies.set('token', token, {
		...options,
		maxAge: 60 * 60 * 24 * 30 // 30 days
	});

export const deleteAuthCookies = (cookies: Cookies) => cookies.delete('token', options);
