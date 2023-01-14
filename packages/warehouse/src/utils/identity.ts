import type { Identity } from "@prisma/client";
import type { IdentityWithUsernames } from '../controllers';

export type SafeIdentity = Omit<Identity, 'password' | 'token' | 'passwordResetToken' | 'passwordResetTokenCreatedAt'> & { usernames: string[] };

export const purifyIdentity = (identity: IdentityWithUsernames): SafeIdentity => {
  const {
    password,
    token,
    passwordResetToken,
    passwordResetTokenCreatedAt,
    ...safeIdentity
  } = identity;

  return {
    ...safeIdentity,
    usernames: safeIdentity.usernames.map(username => username.name)
  };
};
