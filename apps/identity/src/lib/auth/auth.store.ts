import { writable } from 'svelte/store';
import type { SafeIdentity } from '$lib/controllers/identity';

export const IDENTITY = writable<SafeIdentity | null>(null);
