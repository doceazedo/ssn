<script lang="ts">
  import { Hero, Navbar } from '.';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
  export let websiteBaseUrl: string;
  export let identityBaseUrl: string;
  export let currentUrl = '';
  export let playerCount = 0;
</script>

<Hero {websiteBaseUrl} {playerCount} />
<Navbar {websiteBaseUrl} {identityBaseUrl} {currentUrl} {identity} />
