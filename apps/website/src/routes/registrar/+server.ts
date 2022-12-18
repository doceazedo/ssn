import { redirect } from "@sveltejs/kit";
import { env } from '$env/dynamic/public';
import type { RequestHandler } from './$types';

export const GET = (): RequestHandler => {
  throw redirect(301, `${env.PUBLIC_IDENTITY_URL}/auth/register`);
};
