<script lang="ts">
  import { PageTitle } from 'ssnkit';

  export let data;
</script>

<PageTitle pretitle="Geral" title="Conexões" class="mb-6" />

{#if data?.services?.length}
  <div class="notification is-success is-light content">
    Sua conta está conectada com os serviços:
    <ul>
      {#each data.services as service}
        <li>{service}</li>
      {/each}
    </ul>
  </div>
{:else}
  <div class="notification is-danger is-light">
    Nenhum serviço conectado. Você poderá conectar essa conta à outros serviços em breve.
  </div>
{/if}
