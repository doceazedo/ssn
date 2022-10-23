<script lang="ts">
  import { PageTitle } from "ssnkit";
  import { onMount } from "svelte";
  import { browser } from '$app/environment';
  import { DiscordForm } from '$lib/forms';
  import { discordOauthUrl } from '$lib/helpers';
  import { dashboardUrl } from "$lib/env/public";

  export let data;

  onMount(() => {
    if (!browser) return;
    const redirectUrl = localStorage.getItem('redirect') || dashboardUrl;

    // clear localstorage for next flow
    localStorage.removeItem('redirect');
    localStorage.removeItem('invite');

    if (data.identity) window.location = redirectUrl;
  });
</script>

{#if data.identity}
  <PageTitle pretitle="Login" title="Olá, {data.identity.primaryUsername}!" />
  <p>Redirecionando, por favor aguarde...</p>
{:else}
  <PageTitle pretitle="Registrar" title="Finalizar registro" />
  <p>Sempre que você precisar fazer login no SSN.gg, você poderá utilizar sua conta do Discord, por isso, a mantenha em segurança.</p>

  <div class="profile">
    <img class="avatar" src="https://cdn.discordapp.com/avatars/{data.user.id}/{data.user.avatar}.jpg" />
    <p class="user">
      <span class="name">{data.user.username}#{data.user.discriminator}</span>
      <a class="has-text-grey" href={discordOauthUrl}>Não é você?</a>
    </p>
  </div>

  <p>Agora, escolha o nome de usuário que você deseja usar em jogo:</p>
  <DiscordForm refreshToken={data.refreshToken} />
{/if}

<style lang="sass">
  .profile
    display: flex
    gap: 1rem

    .avatar
      height: 4rem
      border-radius: .75rem

    .user
      display: flex
      flex-direction: column
      justify-content: center

      .name
        font-size: 1.25rem
        font-weight: bold
</style>