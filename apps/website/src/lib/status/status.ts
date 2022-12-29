import { SERVER_STATUS } from './status.store';

const baseUrl = '/api/v1';

export const getServerStatus = async () => {
  try {
    const resp = await fetch(`${baseUrl}/status`);
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    SERVER_STATUS.set(data.status);
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}
