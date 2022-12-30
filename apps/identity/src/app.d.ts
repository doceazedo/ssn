// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces
// and what to do when importing types

import type { SafeIdentity } from 'warehouse';

declare global {
  declare namespace App {
    interface Locals {
      identity?: SafeIdentity;
    }
    // interface PageData {}
    // interface PageError {}
    // interface Platform {}
  }

  interface Window {
    turnstile: any;
    onloadTurnstile: any;
  }
}
