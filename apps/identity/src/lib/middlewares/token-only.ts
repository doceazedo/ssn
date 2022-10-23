import { error } from "@sveltejs/kit";
import { catracaToken } from '$lib/env/private';

const tokens = [
  catracaToken
];

export const tokenOnly = async (request: Request, callback: () => any) => {
  const authorizationHeader = request.headers.get("Authorization");
  const token = authorizationHeader?.split('Bearer ')?.[1];
  if (!token) throw error(401, 'Informe um token');
  if (!tokens.includes(token)) throw error(401, 'Token inválido');
  return callback();
}
