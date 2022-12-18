const baseUrl = '/api/v1/invites';

export const createInvite = async () => {
  try {
    const resp = await fetch(baseUrl, {
      method: 'POST'
    });
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    return data.invite;
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}