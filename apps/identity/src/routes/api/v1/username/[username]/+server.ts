import { error, json } from "@sveltejs/kit";
import { createUsername, getUsername, getUsernames } from "warehouse";
import { loggedInOnly, tokenOnly } from "$lib/middlewares";
import type { RequestHandler } from '@sveltejs/kit';
import { validateUsername } from "../../../../../lib/utils";

export const GET: RequestHandler = async ({ request, params }) =>
  tokenOnly(request, async () => {
    if (!params.username) throw error(400);
  
    const username = await getUsername(params.username);
    if (!username) throw error(404);
  
    return json({
      username
    });
  });

export const POST: RequestHandler = async ({ locals, params, cookies }) =>
  loggedInOnly(locals, cookies, async (identity) => {
    if (!identity) throw error(401);
    if (!params.username) throw error(400, 'Informe um nome de usuário');

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
  });
