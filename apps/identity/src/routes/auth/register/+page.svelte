<script lang="ts">
  import { onMount } from "svelte";
  import { browser } from '$app/environment';
  import { CopyIpTag, PageTitle } from 'ssnkit';
  import { Heart, Mail, Users } from 'ssnkit/icons';
  import { RegisterForm } from '$lib/forms';
  import { registerEnabled, inviteOnly, baseDomain } from '$lib/env/public';
  import { IDENTITY } from "$lib/auth";
  import { discordUrl, twitterUrl } from "$lib/helpers/socials";

  export let data;

  const nextSteps = [
    {
      icon: Users,
      // TODO: make this text better?
      label: `Você pode adicionar <b>outros nomes de usuário</b> à sua conta para usar a mesma senha e só precisar logar uma vez através do seu painel em <a href="https://${baseDomain}/alt">${baseDomain}/alt</a>`,
    },
    {
      icon: Mail,
      label: 'Quando puder, confirme o seu endereço de e-mail para receber convites e poder recuperar sua senha no futuro. Lembre-se de conferir sua lixeira e caixa de spam.',
    },
    {
      icon: Heart,
      label: `Participe do <a href="${discordUrl}">nosso Discord</a> e nos <a href="${twitterUrl}">siga no Twitter</a> para fazer parte da comunidade e se manter informado de novidades.`,
    },
  ]

  onMount(() => {
    if (!browser || !data.inviteCode) return;
    localStorage.setItem("invite", data.inviteCode);
  });
</script>

{#if $IDENTITY}
  <PageTitle pretitle="Registrar" title="Tudo pronto! 🎉" />
  <p>Voce já pode jogar no SSN.gg através do IP <CopyIpTag /> :)</p>
  <h2 class="title is-4 mb-0">Informações importantes:</h2>
  <ul class="next-steps">
    {#each nextSteps as step}
      <li>
        <div class="icon">
          <svelte:component this={step.icon} />
        </div>
        <p>{@html step.label}</p>
      </li>
    {/each}
  </ul>
  <a class="button is-primary" href="/me">Meu perfil</a>
{:else}
  <PageTitle pretitle="Registrar" title="Boas-vindas ao SSN.gg!" />
  {#if registerEnabled}
    {#if inviteOnly && !data.invite}
      <div class="is-flex is-flex-direction-column is-flex-grow-1">
        {#if data.inviteCode}
          <div class="notification is-danger is-light">
            O convite informado é inválido ou já foi utilizado.
          </div>
        {:else}
          <div class="notification is-warning is-light">
            Você precisa receber o convite de um outro usuário para se registrar.
          </div>
        {/if}
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
{/if}

<style lang="sass">
  @import '../../../../../../packages/ssnkit/styles/vars'

  .next-steps
    display: flex
    flex-direction: column
    gap: 1.25rem

    li
      display: flex
      gap: .5rem

      .icon
        flex-shrink: 0
        width: 2.5rem
        height: 2.5rem
        border-radius: 50%
        background-color: rgba($primary, .25)
        color: #2c9902
</style>