import { json } from "@sveltejs/kit";
import { validate } from 'deep-email-validator';
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ url }) => {
  const email = url.searchParams.get('email') || '';
  const emailValidation = await validate({
    email,
    validateTypo: false
  });

  return json({
    email,
    emailValidation
  });
};
