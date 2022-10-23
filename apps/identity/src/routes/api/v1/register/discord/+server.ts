import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import {
  createUser,
  generateUserInvites,
  getInvite,
  getUserByName,
  purifyIdentity,
  updateInvite
} from "warehouse";
import { validateRequest } from '$lib/middlewares';
import { setAuthCookies, validateUsername } from '$lib/utils';
import { registerEnabled, inviteOnly, invitesPerUser } from "$lib/env/public";
import { getCurrentAuthorization, refreshAccessToken } from "$lib/services/discord";
import type { RequestHandler } from '@sveltejs/kit';

export type RegisterDiscordParams = {
  refreshToken: string;
  username: string;
  invite?: string;
}

const invalidTokenMessage = 'Seu token de acesso do Discord é inválido, por favor, tente se registrar novamente';

export const POST: RequestHandler = async ({ request, cookies, locals }) =>
  validateRequest<RegisterDiscordParams>(request, yup.object().shape({
    refreshToken: yup.string().required(invalidTokenMessage),
    username: yup.string().min(3, 'O nome de usuário deve ter pelo menos 3 dígitos').max(16, 'O nome de usuário deve ter no máximo 16 dígitos').required('Insira um nome de usuário'),
    invite: yup.string().nullable(),
  }), async (body) => {
    if (!registerEnabled) throw error(400, 'Registro temporariamente desativado');
    if (!!locals.identity) throw error(401, 'Você já está logado');

    if (inviteOnly) {
      if (!body.invite) throw error(400, 'Insira um código de convite');
      const invite = await getInvite(body.invite);
      if (!invite || invite.usedById) throw error(410, 'Código de convite inválido ou já utilizado');
      // TODO: check if invite owner has validated their email
    }

    const isValidUsername = validateUsername(body.username);
    if (!isValidUsername) throw error(400, 'Insira um nome de usuário válido');

    const existingUsername = await getUserByName(body.username);
    if (existingUsername) throw error(409, 'Nome de usuário já registrado');

    const tokens = await refreshAccessToken(body.refreshToken);
    if (!tokens) throw error(400, invalidTokenMessage);

    const authorization = await getCurrentAuthorization(tokens.access_token);
    if (!authorization) throw error(400, invalidTokenMessage);

    const identity = await createUser({
      primaryUsername: body.username,
      usernames: {
        create: [
          { name: body.username },
        ],
      },
      connections: {
        create: [
          {
            id: authorization.user.id,
            service: 'DISCORD',
            accessToken: tokens.access_token,
            expiresAt: authorization.expires,
            refreshToken: tokens.refresh_token,
            scope: authorization.scopes.join(' '),
            tokenType: tokens.token_type,
          }
        ]
      }
    });
    if (!identity) throw error(500, 'Ocorreu um erro, por favor tente novamente.');
    const safeIdentity = purifyIdentity(identity);

    if (inviteOnly && body.invite) {
      await updateInvite(body.invite, {
        usedById: identity.uuid
      });
      await generateUserInvites(identity.uuid, invitesPerUser);
    }

    setAuthCookies(cookies, identity.token);
    return json({
      identity: safeIdentity
    });
  });
