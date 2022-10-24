<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { IDENTITY, logout } from "$lib/auth";
</script>

<PageTitle pretitle="Perfil" title={$IDENTITY?.primaryUsername} class="mb-6" />
<pre class="card my-4">{JSON.stringify($IDENTITY, null, 2)}</pre>

<button class="button is-danger is-small" on:click={logout}>
  Desconectar
</button>