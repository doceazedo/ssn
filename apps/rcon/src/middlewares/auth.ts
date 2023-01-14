import { Server } from "socket.io";
import { parse } from 'cookie';
import { IdentityWithUsernames } from "warehouse";

const whoami = async (cookies: string): Promise<IdentityWithUsernames> => {
  try {
    const resp = await fetch(`${process.env.LOCAL_IDENTITY_URL}/api/v1/whoami`, {
      headers: {
        Cookie: cookies
      }
    });
    const data = await resp.json();
    if (!resp.ok || !data.identity) throw Error(data.message || 'Erro desconhecido');
    return data.identity;
  } catch (e: any) {
    throw Error(e.message || 'Erro desconhecido');
  }
}

export const registerAuthMiddlewares = (io: Server) => {
  io.use(async (socket, next) => {
    const cookieHeaders = socket.request.headers.cookie || '';
    const cookies = parse(cookieHeaders);
    if (!cookies.token) return next(Error('Unauthorized'));

    try {
      const identity = await whoami(cookieHeaders);
      if (identity.role !== 'ADMIN') return next(Error('Forbbiden'));

      next();
    } catch (e: any) {
      next(Error(e.message || 'Erro desconhecido'));
    }
  })
}
