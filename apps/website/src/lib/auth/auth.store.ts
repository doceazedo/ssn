import { writable } from 'svelte/store';
import type { SafeIdentity } from 'warehouse';

export const IDENTITY = writable<SafeIdentity | null>(null);
