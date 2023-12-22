import { error, json } from '@sveltejs/kit';
import * as yup from 'yup';
import {
	createUsername,
	deleteUsername,
	getUserByName,
	getUsername,
	getUsernames,
	updateUser,
	updateUsername
} from 'warehouse';
import { loggedInOnly, tokenOnly, validateRequest } from '$lib/middlewares';
import { validateUsername } from '$lib/utils';
import { validateCaptcha } from '$lib/captcha/validate-captcha';
import type { RequestHandler } from '@sveltejs/kit';

export type CreateUsernameParams = {
	captchaToken?: string;
};

export type UpdateUsernameParams = {
	newUsername: string;
};

export type DeleteUsernameParams = {
	deleteIngameData?: boolean;
};

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
	validateRequest<CreateUsernameParams>(
		request,
		yup.object().shape({
			captchaToken: yup.string().nullable()
		}),
		async (body) =>
			loggedInOnly(locals, cookies, async (identity) => {
				if (!identity) throw error(401);
				if (!params.username) throw error(400, 'Informe um nome de usuário');

				const hasValidCaptcha = await validateCaptcha(body.captchaToken);
				if (!hasValidCaptcha) throw error(400, 'Captcha inválido');

				const usernames = await getUsernames(identity.uuid);
				const hasPlayedBefore = usernames.every((username) => !!username.firstJoin);
				if (!hasPlayedBefore)
					throw error(
						400,
						'Você deve jogar pelo menos uma vez com todos os seus outros usuários antes de criar um novo'
					);

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

export const DELETE: RequestHandler = async ({ request, locals, cookies, params }) =>
	validateRequest<DeleteUsernameParams>(
		request,
		yup.object().shape({
			deleteIngameData: yup.boolean().nullable()
		}),
		async (body) =>
			loggedInOnly(locals, cookies, async (identity) => {
				if (!params.username) throw error(400);

				// TODO: remove me later
				throw error(501, 'Não foi possível desconectar o usuário do servidor');

				// TODO: to be implemented
				if (body.deleteIngameData)
					throw error(
						501,
						'No momento não é possível apagar o inventário em jogo. Desative essa opção, ou tente novamente no futuro™'
					);

				const username = identity.usernames.find((user) => user.name === params.username);
				if (!username) throw error(403, 'Esse nome de usuário não pertence à sua conta');

				if (identity.primaryUsername === username.name)
					throw error(400, 'Não é possível apagar sua conta principal');

				const deletedUsername = await deleteUsername(username.name);
				if (!deletedUsername) throw error(500);

				if (body.deleteIngameData) {
					// call commander to kick user
				} else {
					// call commander to delete ingame data
				}

				return json({
					username
				});
			})
	);

export const PUT: RequestHandler = async ({ request, params, locals, cookies }) =>
	loggedInOnly(locals, cookies, async (identity) =>
		validateRequest<UpdateUsernameParams>(
			request,
			yup.object().shape({
				newUsername: yup.string()
			}),
			async (body) => {
				if (!params.username) throw error(400);

				const username = await getUsername(params.username);
				if (!username) throw error(404);

				const owner = await getUserByName(username.name);
				if (!owner) throw error(404);

				if (owner.usernames.length !== 1)
					throw error(
						400,
						'Você não pode atualizar esse nome de usuário, ao invés disso, crie uma nova conta no seu painel'
					);

				if (username.ownerId !== identity.uuid) throw error(401);

				if (username.firstJoin)
					throw error(
						400,
						'Você não pode atualizar o nome de usuário de uma conta que já se conectou no servidor, ao invés disso, crie uma nova conta no seu painel'
					);

				if (username.isOnline)
					throw error(
						400,
						'Por favor, desconecte sua conta do servidor antes de alterar seu nome de usuário'
					);

				const isValidUsername = validateUsername(body.newUsername);
				if (!isValidUsername) throw error(400, 'Insira um nome de usuário válido');

				const existingUsername = await getUsername(body.newUsername);
				if (existingUsername) throw error(409, 'Esse nome de usuário já está em uso');

				const updatedUsername = await updateUsername(username.name, {
					name: body.newUsername
				});
				if (!updatedUsername) throw error(500, 'Não foi possível atualizar o seu nome de usuário');

				if (owner.primaryUsername == params.username) {
					const updatedUser = await updateUser(owner.uuid, {
						primaryUsername: updatedUsername.name
					});
					if (!updatedUser)
						throw error(500, 'Não foi possível atualizar seu nome de usuário principal');
				}

				return json({
					username: updatedUsername
				});
			}
		)
	);
