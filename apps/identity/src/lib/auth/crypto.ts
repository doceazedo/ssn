import sha256 from 'crypto-js/sha256';
import { randomBytes } from 'crypto';

const encryptPassword = (password: string, salt: string) =>
  sha256(salt + password).toString();

export const hashPassword = (password: string) => {
  const salt = randomBytes(16).toString('hex');
  const hash = encryptPassword(password, salt);
  return hash + salt;
}

export const matchPassword = (password: string, hash: string) => {
  const salt = hash.slice(64);
  const originalPassHash = hash.slice(0, 64);
  const currentPassHash = encryptPassword(password, salt);
  return originalPassHash == currentPassHash;
}
