import type { AuthRecord, RecordAuthResponse, RecordModel } from 'pocketbase';
import { writable } from 'svelte/store';

export const USER = writable<AuthRecord | null>(null);

export const PRIMARY_USERNAME = writable<RecordModel | null>(null);

export const AUTH_DATA = writable<RecordAuthResponse<RecordModel> | null>(null);
