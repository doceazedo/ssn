import { error, json } from "@sveltejs/kit";
import * as yup from 'yup';
import { createUsername, deleteUsername, getUserByName, getUsername, getUsernames } from "warehouse";
import { loggedInOnly, tokenOnly, validateRequest } from "$lib/middlewares";
import { validateUsername } from "$lib/utils";
import { validateCaptcha } from "$lib/captcha/validate-captcha";
import type { RequestHandler } from '@sveltejs/kit';

export type CreateUsernameParams = {
  captchaToken?: string;
}

export const GET: RequestHandler = async ({ request, params }) =>
  tokenOnly(request, async () => {
    if (!params.username) throw error(400);
  
    const username = await getUsername(params.username);
    if (!username) throw error(404);
  
    return json({
      username
    });
  });

export const POST: RequestHandler = async ({ request, locals, params, cookies }) =>
  validateRequest<CreateUsernameParams>(request, yup.object().shape({
    captchaToken: yup.string().nullable()
  }), async (body) =>
    loggedInOnly(locals, cookies, async (identity) => {
      if (!identity) throw error(401);
      if (!params.username) throw error(400, 'Informe um nome de usuário');

      const hasValidCaptcha = await validateCaptcha(body.captchaToken);
      if (!hasValidCaptcha) throw error(400, 'Captcha inválido');

      const usernames = await getUsernames(identity.uuid);
      const hasPlayedBefore = usernames.every(username => !!username.firstJoin);
      if (!hasPlayedBefore) throw error(400, 'Você deve jogar pelo menos uma vez com todos os seus outros usuários antes de criar um novo');

      const isValidUsername = validateUsername(params.username);
      if (!isValidUsername) throw error(400, 'Insira um nome de usuário válido');

      const username = await getUsername(params.username);
      if (username) throw error(409, 'Esse nome de usuário já está em uso');

      const newUsername = await createUsername(params.username, identity.uuid);
      if (!newUsername) throw error(500, 'Não foi possível criar novo usuário');

      return json({
        username: newUsername
      });
    })
  );

export const DELETE: RequestHandler = async ({ request, params }) =>
  tokenOnly(request, async () => {
    if (!params.username) throw error(400);
  
    const username = await getUsername(params.username);
    if (!username) throw error(404);

    const owner = await getUserByName(username.name);
    if (!owner) throw error(404);
    if (owner.primaryUsername === username.name) throw error(400, 'Cannot delete primary username');

    const deletedUsername = await deleteUsername(username.name);
    if (!deletedUsername) throw error(500);
  
    return json({
      username: deletedUsername
    });
  });
