import { writable } from 'svelte/store';
import { getServerStatus } from './status';
import { browser } from '$app/environment';
import type { JavaStatusResponse } from 'minecraft-server-util';

const updateFrequency = 45000;

export const SERVER_STATUS = writable<JavaStatusResponse | null>(null, () => {
  if (!browser) return;
  getServerStatus();
  setInterval(getServerStatus, updateFrequency);
});
