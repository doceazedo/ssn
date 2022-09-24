import { error } from "@sveltejs/kit";

export const protectedEndpoint = async (locals: App.Locals, callback: () => any) => {
  if (!locals.identity) throw error(401, 'Você não está logado');
  return callback();
}