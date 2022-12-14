import { redirect } from "@sveltejs/kit";
import type { RequestHandler } from './$types';

export const GET = (): RequestHandler => {
  throw redirect(301, 'https://discord.gg/DChTnVTuKp');
};
