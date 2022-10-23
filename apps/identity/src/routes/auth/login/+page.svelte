<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { LoginForm } from '../../../lib/forms';
  import { onMount } from "svelte";
  import { browser, } from "$app/environment";
  import { page } from "$app/stores";

  onMount(() => {
    if (!browser) return;

    const redirect = $page.url.searchParams.get('redirect');
    if (redirect) localStorage.setItem('redirect', redirect);
  });
</script>

<PageTitle pretitle="Login" title="É um prazer te ver de novo." />
<LoginForm />