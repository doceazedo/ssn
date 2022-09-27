import { json } from "@sveltejs/kit";
import { protectedEndpoint } from '$lib/middlewares';
import { deleteAuthCookies } from '$lib/utils';
import type { RequestHandler } from '@sveltejs/kit';

export const POST: RequestHandler = async ({ cookies, locals }) =>
  protectedEndpoint(locals, () => {
    deleteAuthCookies(cookies);
    return json({
      identity: null
    });
  });
