<script lang="ts">
  import { Hero, Navbar } from '.';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
  export let websiteBaseUrl: string;
  export let identityBaseUrl: string;
  export let currentUrl = '';
</script>

<Hero />
<Navbar {websiteBaseUrl} {identityBaseUrl} {currentUrl} {identity} />
