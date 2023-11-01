import { writable } from "svelte/store";
import { browser } from "$app/environment";

export type Status = {
  online: boolean;
  host: string;
  port: number;
  ip_address: string;
  eula_blocked: boolean;
  retrieved_at: number;
  expires_at: number;
  version: {
    name_raw: string;
    name_clean: string;
    name_html: string;
    protocol: number;
  };
  players: {
    online: number;
    max: number;
  };
  motd: {
    raw: string;
    clean: string;
    html: string;
  };
  icon: string;
  srv_record: {
    host: string;
    port: number;
  };
};

export const SERVER_STATUS = writable<Status | null>(null, () => {
  if (!browser) return;
  getServerStatus();
  setInterval(getServerStatus, 60000);
});

const getServerStatus = async (ip = "ssn.gg") => {
  try {
    const resp = await fetch(`https://api.mcstatus.io/v2/status/java/${ip}`);
    const data = await resp.json();
    SERVER_STATUS.set(data);
  } catch (error) {
    SERVER_STATUS.set(null);
  }
};
