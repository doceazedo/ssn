const baseUrl = '/api/v1';

export const login = async (login: string, password: string, redirectTo?: string) => {
  try {
    const resp = await fetch(`${baseUrl}/login`, {
      method: 'POST',
      body: JSON.stringify({ login, password })
    });
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    console.log('redirectTo', redirectTo);
  } catch (e: any) {
    console.log(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}
