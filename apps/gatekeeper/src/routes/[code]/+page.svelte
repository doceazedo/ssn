<script lang="ts">
  import { onMount } from "svelte";
  import { Checkbox, RadioGroup } from 'ssnkit';
  import type { PageServerData } from './$types';

  export let data: PageServerData;

  const options = [
    { value: 5,     label: 'Permitir dessa vez' },
    { value: 86400, label: 'Permitir esse IP por 24 horas' },
    { value: -1,    label: 'Sempre permitir esse IP' },
  ];
  let ttl = 5;
  let grantAlts = true;
  let region = null;

  onMount(() => {
    if (!data.location) return;
    region = `${data.location.city}, ${data.location.region}${
      data.location.country != 'BR' ? ` (${data.location.country})` : ''
    }`
  })
</script>

<h1 class="title">Liberar acesso</h1>
<p>
  Você está tentando se conectar com o usuário <b>{data.username}</b>
  {#if region}
    na região de <b>{region}</b>
  {/if}
</p>

<div class="controls">
  <RadioGroup {options} bind:value={ttl} />
  <Checkbox bind:checked={grantAlts}>
    Também autorizar minhas contas alternativas
  </Checkbox>
</div>

<div class="buttons">
  <button class="button">Cancelar</button>
  <a class="button is-primary" href="/grant?code={data.code}&ttl={ttl}&grantAlts={grantAlts}">Permitir</a>
</div>

<style lang="sass">
  .controls
    display: flex
    flex-direction: column
    gap: 1rem
    margin-bottom: 1rem

  .buttons
    width: 100%
    justify-content: flex-end
</style>