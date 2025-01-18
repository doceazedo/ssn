import { error, json } from '@sveltejs/kit';
import * as yup from 'yup';
import { matchPassword } from '$lib/auth/crypto';
import { getUserByEmailOrName, purifyIdentity } from 'warehouse';
import { validateRequest } from '$lib/middlewares';
import { setAuthCookies } from '$lib/utils';
import { validateCaptcha } from '$lib/captcha/validate-captcha';
import type { RequestHandler } from '@sveltejs/kit';

type LoginParams = {
	login: string;
	password: string;
	captchaToken?: string;
};

export const POST: RequestHandler = async ({ request, cookies, locals }) =>
	validateRequest<LoginParams>(
		request,
		yup.object().shape({
			login: yup.string().required('Insira um e-mail ou nome de usuário válidos'),
			password: yup.string().min(6, 'Login ou senha inválidos').required('Insira uma senha'),
			captchaToken: yup.string().nullable()
		}),
		async (body) => {
			if (locals.identity) throw error(401, 'Você já está logado');

			const identity = await getUserByEmailOrName(body.login);
			if (!identity) throw error(401, 'Login ou senha inválidos');

			const hasValidCaptcha = await validateCaptcha(body.captchaToken);
			if (!hasValidCaptcha) throw error(400, 'Captcha inválido');

			const isValidPassword = identity.password && matchPassword(body.password, identity.password);
			if (!isValidPassword) throw error(401, 'Login ou senha inválidos');

			const safeIdentity = purifyIdentity(identity);

			setAuthCookies(cookies, identity.token);
			return json({
				identity: safeIdentity
			});
		}
	);
