import { handleIdentity } from 'ssnkit/hooks';
import type { Handle } from '@sveltejs/kit';

export const handle: Handle = handleIdentity;
