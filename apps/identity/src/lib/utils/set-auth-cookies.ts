import { dev } from "$app/environment";
import type { Cookies } from "@sveltejs/kit";

export const setAuthCookies = (cookies: Cookies, token: string) =>
  cookies.set('token', token, {
    httpOnly: true,
    maxAge: 60 * 60 * 24 * 30, // 30 days
    sameSite: 'strict',
    secure: false, //!dev
  });
