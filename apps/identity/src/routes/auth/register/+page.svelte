<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { RegisterForm } from '../../../lib/forms';
  import { registerEnabled, inviteOnly } from '../../../lib/env/public';
</script>

<PageTitle pretitle="Registrar" title="Boas-vindas ao ssn.gg!" />
{#if registerEnabled}
  {#if inviteOnly}
    <div class="is-flex is-flex-direction-column is-flex-grow-1">
      <div class="notification is-warning is-light">
        Você precisa receber o convite de um outro usuário para se registrar.
      </div>
      <div class="notification is-danger is-light">
        O convite informado é inválido ou já foi utilizado.
      </div>
      <p class="mt-auto"><a class="has-text-grey" href="/login">Já possui uma conta?</a></p>
    </div>
  {:else}
    <RegisterForm />
  {/if}
{:else}
  <div class="is-flex is-flex-direction-column is-flex-grow-1">
    <div class="notification is-warning is-light">
      Agradecemos o seu interesse em jogar em nosso servidor! Infelizmente <b>não estamos aceitando
      registros</b> de novos jogadores no momento, mas atente-se ao nosso
      <a href="https://twitter.com/servidorsemnome" target="_blank">Twitter</a>
      para ficar sabendo quando abrirmos os cadastros novamente.
    </div>
    <p class="mt-auto"><a class="has-text-grey" href="/login">Já possui uma conta?</a></p>
  </div>
{/if}