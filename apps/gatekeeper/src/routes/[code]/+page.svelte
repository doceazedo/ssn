<script lang="ts">
  import { RadioGroup } from 'ssnkit';
  import ssnIcon from 'ssnkit/assets/img/ssn-icon.png';
  import type { PageServerData } from './$types';

  export let data: PageServerData;

  const options = [
    { value: 'once',    label: 'Permitir dessa vez' },
    { value: '24h',     label: 'Permitir esse IP por 24 horas' },
    { value: 'forever', label: 'Sempre permitir esse IP' },
  ];
  let grantType = 'once';

  const getRegion = () =>
    `${data.location.city}, ${data.location.region}${
      data.location.country != 'BR' ? ` (${data.location.country})` : ''
    }`;
</script>

<header class="header">
  <img src={ssnIcon} />
</header>

<div class="card">
  <div class="card-content">
    <div class="content">
      <h1 class="title">Liberar acesso</h1>
      <p>Você está tentando se conectar com o usuário <b>{data.nickname}</b>
        na região de <b>{getRegion()}</b>.</p>

      <div class="radio-group">
        <RadioGroup {options} bind:value={grantType} />
      </div>

      <div class="buttons">
        <button class="button">Cancelar</button>
        <button class="button is-primary">Permitir</button>
      </div>
    </div>
  </div>
</div>

<style lang="sass">
  .header
    display: flex
    justify-content: center
    margin-bottom: 2rem

    img
      height: 6rem

  .radio-group
    margin-bottom: 1rem

  .buttons
    width: 100%
    justify-content: flex-end
</style>