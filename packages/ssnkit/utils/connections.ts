import { DiscordIcon } from "..";
import type { SvelteComponent } from "svelte";

export type ServiceProfile = {
  username: string;
  avatar: string;
  url: string;
};

type ServiceDetails = {
  [service: string]: {
    name: string;
    badge: string;
    icon: typeof SvelteComponent;
  };
};

export const SERVICE_DETAILS: ServiceDetails = {
  DISCORD: {
    name: "Discord",
    badge: "/img/connections/discord-badge.jpg",
    icon: DiscordIcon,
  },
};
