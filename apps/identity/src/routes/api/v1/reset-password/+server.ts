import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import { v4 as uuidv4 } from 'uuid';
import { getUserByEmail, updateUser } from "warehouse";
import { validateRequest } from "$lib/middlewares";
import { validateCaptcha } from "$lib/captcha/validate-captcha";
import type { RequestHandler } from '@sveltejs/kit';
import { sendEmail } from "$lib/utils/send-email";
import { RESET_PASSWORD_TEMPLATE } from "$lib/utils/send-email/templates";
import { env } from "$env/dynamic/public";

type ResetPasswordParams = {
  email: string;
  captchaToken?: string;
}

const TOKEN_COOLDOWN = 60000; // 1 minute

export const POST: RequestHandler = async ({ request, locals }) =>
  validateRequest<ResetPasswordParams>(request, yup.object().shape({
    email: yup.string().email('Insira um e-mail válido').required('Insira um endereço de e-mail'),
    captchaToken: yup.string().nullable(),
  }), async (body) => {
    if (locals.identity) throw error(401, 'Você já está logado');

    const hasValidCaptcha = await validateCaptcha(body.captchaToken);
    if (!hasValidCaptcha) throw error(400, 'Captcha inválido');

    const identity = await getUserByEmail(body.email);
    if (!identity) return json(null);

    const currentTime = Date.now();
    const tokenCreatedTime = identity.passwordResetTokenCreatedAt?.getTime();
    if (tokenCreatedTime && currentTime - tokenCreatedTime < TOKEN_COOLDOWN) {
      throw error(429, 'Aguarde antes de solicitar outro link');
    }

    const updatedUser = await updateUser(identity.uuid, {
      passwordResetToken: uuidv4(),
      passwordResetTokenCreatedAt: new Date()
    });
    if (!updatedUser) throw error(500);

    const userEmail = identity.email || '';
    const resetUrl = `${env.PUBLIC_IDENTITY_URL}/auth/reset-password/${updatedUser.passwordResetToken}`;
    try {
      await sendEmail(
        userEmail,
        RESET_PASSWORD_TEMPLATE(updatedUser.primaryUsername, resetUrl)
      );
    } catch (e: any) {
      throw error(500, e.message || 'Não foi possível enviar o e-mail, tente novamente mais tarde');
    }

    return json(null);
  });
