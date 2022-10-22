import { redirect } from "@sveltejs/kit";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ params }) => {
  const { code } = params;
  throw redirect(302, `/auth/register?invite=${code}`);
};
