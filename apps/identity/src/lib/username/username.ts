import type { SafeIdentity } from "warehouse";

const baseUrl = '/api/v1/username';

export const updatePrimaryUsername = async (username: string): Promise<SafeIdentity> => {
  try {
    const resp = await fetch(`${baseUrl}/${username}/primary`, {
      method: 'POST',
    });
    const data = await resp.json();
    if (!resp.ok || !data.identity) throw Error(data.message || 'Erro desconhecido');
    return data.identity;
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}
