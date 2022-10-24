import { json } from "@sveltejs/kit";
import { loggedInOnly } from '$lib/middlewares';
import { deleteAuthCookies } from '$lib/utils';
import type { RequestHandler } from '@sveltejs/kit';

export const POST: RequestHandler = async ({ cookies, locals }) =>
  loggedInOnly(locals, null, () => {
    deleteAuthCookies(cookies);
    return json({
      identity: null
    });
  });
