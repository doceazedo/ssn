import { goto } from '$app/navigation';
import { dashboardUrl } from '$lib/env/public';
import { IDENTITY } from "./auth.store";

export type RegisterParams = {
  email: string;
  password: string;
  username: string;
}

const baseUrl = '/api/v1';

export const login = async (login: string, password: string, redirectTo?: string) => {
  if (!login || !password) throw Error('Insira um login e/ou senha válidos');

  try {
    const resp = await fetch(`${baseUrl}/login`, {
      method: 'POST',
      body: JSON.stringify({ login, password })
    });
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    IDENTITY.set(data.identity);
    await goto(redirectTo || dashboardUrl);
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}

export const register = async (params: RegisterParams, invite?: string, redirectTo?: string) => {
  const isFilled = Object.values(params).every(x => x);
  if (!isFilled) throw Error('Preencha todos os campos');

  try {
    const resp = await fetch(`${baseUrl}/register`, {
      method: 'POST',
      body: JSON.stringify({ ...params, invite })
    });
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    IDENTITY.set(data.identity);
    await goto(redirectTo || dashboardUrl);
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}

export const logout = async () => {
  try {
    const resp = await fetch(`${baseUrl}/logout`, {
      method: 'POST',
    });
    const data = await resp.json();
    if (!resp.ok) throw Error(data.message || 'Erro desconhecido');
    IDENTITY.set(null);
    await goto('/auth/login');
  } catch (e: any) {
    console.error(e);
    throw Error(e.message || 'Erro desconhecido');
  }
}
