import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import * as bcrypt from 'bcrypt';
import { env } from '$env/dynamic/private';
import { createUser, getUserByEmail, getUserByName, purifyIdentity } from "$lib/controllers/identity";
import { validateRequest } from '$lib/middlewares';
import { setAuthCookies, validateUsername } from '$lib/utils';
import type { RequestHandler } from '@sveltejs/kit';

type RegisterParams = {
  email: string;
  password: string;
  username: string;
  invite?: string;
}

const registerEnabled = env.REGISTER_ENABLED === 'true';
const inviteOnly = env.REGISTER_INVITE_ONLY === 'true';
const invitesPerUser = parseInt(env.REGISTER_INVITES_PER_USER) || 0;

export const POST: RequestHandler = async ({ request, cookies, locals }) =>
  validateRequest<RegisterParams>(request, yup.object().shape({
    email: yup.string().email('Insira um e-mail válido').required('Insira um endereço de e-mail'),
    password: yup.string().min(6, 'A senha deve ter pelo menos 6 dígitos').required('Insira uma senha'),
    username: yup.string().min(3, 'O nome de usuário deve ter pelo menos 3 dígitos').max(16, 'O nome de usuário deve ter no máximo 16 dígitos').required('Insira um nome de usuário'),
    invite: yup.string(),
  }), async (body) => {
    if (!registerEnabled) throw error(409, 'Registro temporariamente desativado');

    if (!!locals.identity) throw error(401, 'Você já está logado');

    if (inviteOnly) {
      if (!body.invite) throw error(400, 'Insira um código de convite');
      // TODO: check if invite exists and is not used
      // and if the owner has validated their email
    }

    const isValidUsername = validateUsername(body.username);
    if (!isValidUsername) throw error(400, 'Insira um nome de usuário válido');

    const existingUsername = await getUserByName(body.username);
    if (existingUsername) throw error(409, 'Nome de usuário já registrado');

    // const isValidEmail;
    // TODO: https://github.com/mfbx9da4/deep-email-validator

    const existingEmail = await getUserByEmail(body.email);
    if (existingEmail) throw error(409, 'Endereço de e-mail já registrado');

    const salt = await bcrypt.genSalt(10);
    const password = await bcrypt.hash(body.password, salt);

    // TODO: create x invites, if enabled

    const identity = await createUser({
      email: body.email,
      primaryUsername: body.username,
      usernames: {
        create: [
          { name: body.username },
        ],
      },
      password,
    });
    if (!identity) throw error(500, 'Ocorreu um erro, por favor tente novamente.');
    const safeIdentity = purifyIdentity(identity);

    // TODO: set invite as used

    setAuthCookies(cookies, identity.token);
    return json({
      identity: safeIdentity
    });
});
