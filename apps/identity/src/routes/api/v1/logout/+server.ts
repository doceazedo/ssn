import { json } from "@sveltejs/kit";
import { protectedEndpoint } from '$lib/middlewares';
import type { RequestHandler } from '@sveltejs/kit';

export const POST: RequestHandler = async ({ cookies, locals }) =>
  protectedEndpoint(locals, () => {
    cookies.delete('token');
    return json({
      identity: null
    });
  });
