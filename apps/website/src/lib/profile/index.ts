import type { ProfileLike } from '@prisma/client';

const baseUrl = '/api/v1';

export const toggleProfileLike = async (profileId: string): Promise<ProfileLike | null> => {
  try {
    const resp = await fetch(`${baseUrl}/profile/${profileId}/like`, {
      method: 'POST'
    });
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    return data;
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}
