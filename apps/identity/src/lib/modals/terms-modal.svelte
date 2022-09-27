<script lang="ts">
  import { fade, scale } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';

  export let isOpen = false;
  export let handleAccept: () => void;

  const close = () => isOpen = false;
</script>

{#if isOpen}
  <div class="modal is-active" transition:fade={{ duration: 200, easing: quintOut }}>
    <div class="modal-background" on:click={close}></div>
    <div class="modal-card" transition:scale={{ duration: 200, opacity: 1, start: .75, easing: quintOut }}>
      <header class="modal-card-head">
        <p class="modal-card-title">Termos de uso</p>
        <button class="delete" aria-label="close" on:click={close} />
      </header>
      <section class="modal-card-body">
        <div class="content">
          <p>Ao se registrar no SSN.gg você entende e concorda com os seguintes pontos:</p>
          <ul>
            <li><b>✅ Sou responsável pelas minhas próprias ações.</b> Tenho ciência de que meus atos são de minha própria responsabilidade, considerando ainda que o SSN.gg está hospedado no Brasil e segue a lesgislação deste país e que há um empenho ativo para reforçar que a mesma seja aplicada.</li>
            <li><b>✅ A privacidade do outro deve ser respeitada.</b> As interações devem ser mantidas dentro do servidor, buscar e divulgar informações pessoais de outras pessoas <u>nao será tolerado</u>.</li>
            <li><b>✅ Não devo prejudicar a estabilidade do servidor.</b> Não há limitações do que pode ser feito dentro do servidor, desde que a estabilidade do mesmo seja preservada.</li>
            <li><b>✅ O conteúdo dos jogadores não é moderado.</b> Muito do que pode ser encontrado no servidor pode ser considerado ofensivo e sem filtro.</li>
            <li><b>✅ Serei respeitoso nas comunidades do SSN.gg.</b> Apesar do servidor não ter regras, eu entendo que as comunidades fora do jogo são moderadas e devem ser espaços receptivos a <u>todas as pessoas</u>.</li>
          </ul>
          <p>Ir contra qualquer uma dessas diretrizes pode ocasionar no bloqueio temporário ou permanente, parcial ou integral, do seu acesso às plataformas do SSN.gg.</p>
        </div>
      </section>
      <footer class="modal-card-foot">
        <button class="button is-primary" on:click={handleAccept}>Estou de acordo</button>
        <button class="button" on:click={close}>Cancelar</button>
      </footer>
    </div>
  </div>
{/if}