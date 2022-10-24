<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { IDENTITY, logout } from "$lib/auth";

  $: s = $IDENTITY.usernames.length > 1 ? 's' : '';
</script>

<PageTitle pretitle="Painel" title={$IDENTITY?.primaryUsername} class="mb-6" />

<div class="notification is-warning is-light">
  Você tem <b>{$IDENTITY.usernames.length} usuário{s}</b> cadastrado{s}.
  Para criar mais um, acesse <a href="/dashboard/usernames">Minhas contas</a>.
</div>

<button class="button is-danger is-small" on:click={logout}>
  Desconectar
</button>