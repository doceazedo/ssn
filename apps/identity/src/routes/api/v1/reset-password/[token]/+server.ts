import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import { v4 as uuidv4, validate as validateUUID } from 'uuid';
import { getUserByPasswordResetToken, purifyIdentity, updateUser } from "warehouse";
import { validateRequest } from "$lib/middlewares";
import { validateCaptcha } from "$lib/captcha/validate-captcha";
import type { RequestHandler } from '@sveltejs/kit';
import { hashPassword } from "$lib/auth/crypto";
import { setAuthCookies } from "$lib/utils";
import { sendEmail } from "$lib/utils/send-email";
import { PASSWORD_CHANGED_TEMPLATE } from "$lib/utils/send-email/templates";

type ResetPasswordParams = {
  password: string;
  captchaToken?: string;
}

const TOKEN_TTL = 60000 * 60; // 1 hour

export const POST: RequestHandler = async ({ request, locals, params, cookies }) =>
  validateRequest<ResetPasswordParams>(request, yup.object().shape({
    password: yup.string().min(6, 'A senha deve ter pelo menos 6 dígitos').required('Insira uma senha'),
    captchaToken: yup.string().nullable(),
  }), async (body) => {
    if (locals.identity) throw error(401, 'Você já está logado');
    if (!params.token || !validateUUID(params.token))
      throw error(400, 'Token de redefinição de senha inválido');

    const hasValidCaptcha = await validateCaptcha(body.captchaToken);
    if (!hasValidCaptcha) throw error(400, 'Captcha inválido');

    const identity = await getUserByPasswordResetToken(params.token);
    if (!identity) throw error(400, 'Token de redefinição de senha inválido');

    const currentTime = Date.now();
    const tokenCreatedTime = identity.passwordResetTokenCreatedAt?.getTime();
    if (tokenCreatedTime && currentTime - tokenCreatedTime > TOKEN_TTL) {
      throw error(410, 'Esse link de redefinição de senha expirou. Por favor, solicite um novo.');
    }

    const password = hashPassword(body.password);
    const token = uuidv4();

    const updatedUser = await updateUser(identity.uuid, {
      password,
      token,
      passwordResetToken: null,
    });
    if (!updatedUser) throw error(500);

    const safeIdentity = purifyIdentity(identity);
    
    const userEmail = updatedUser.email || '';
    try {
      await sendEmail(
        userEmail,
        PASSWORD_CHANGED_TEMPLATE(updatedUser.primaryUsername)
      );
    } catch (e) {
      // it's ok if the email is not sent here :)
    }

    setAuthCookies(cookies, updatedUser.token);
    return json({
      identity: safeIdentity
    });
  });
