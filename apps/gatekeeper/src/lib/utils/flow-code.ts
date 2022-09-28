import { v4 as uuidv4 } from 'uuid';

export const generateCode = () => uuidv4().slice(0, 5);

export const isValidCode = (code: string) => /^[a-zA-Z0-9]{5}$/.test(code);
