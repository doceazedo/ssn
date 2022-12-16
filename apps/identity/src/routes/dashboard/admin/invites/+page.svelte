<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { createInvite } from '$lib/invites';

  let lastCreatedInvite: any;
</script>

<PageTitle pretitle="Administração" title="Convites" class="mb-6" />

<button class="button is-primary" on:click={async () => lastCreatedInvite = await createInvite()}>
  Gerar convite
</button>

{#if lastCreatedInvite}
  <pre>{JSON.stringify(lastCreatedInvite, null, 2)}</pre>
{/if}