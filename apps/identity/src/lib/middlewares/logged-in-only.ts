import { error } from "@sveltejs/kit";
import type { Cookies } from "@sveltejs/kit";
import { getUserByToken } from "warehouse";
import type { IdentityWithUsernames } from "warehouse";

export const loggedInOnly = async (locals: App.Locals, cookies: Cookies | null, callback: (identity?: IdentityWithUsernames) => any) => {
  if (!locals.identity) throw error(401, 'Você não está logado');
  if (!cookies) return callback();

  const token = cookies.get('token');
  if (!token) throw error(401, 'Você não está logado');

  const identity = await getUserByToken(token);
  if (!identity) throw error(401, 'Usuário inválido');

  return callback(identity);
}
