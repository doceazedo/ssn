<script lang="ts">
  import { BookOpenCheck, Home, MessageCircle, Users, Wallet } from 'lucide-svelte';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
  export let websiteBaseUrl: string;
  export let identityBaseUrl: string;
  export let currentUrl = '';

  const navbarItems = [
    {
      label: 'Início',
      href: websiteBaseUrl,
      icon: Home
    },
    {
      label: 'Doar',
      href: `${websiteBaseUrl}/donate`,
      icon: Wallet
    },
    {
      label: 'Etiqueta',
      href: `${websiteBaseUrl}/rules`,
      icon: BookOpenCheck
    },
    {
      label: 'Comunidade',
      href: `${websiteBaseUrl}/community`,
      icon: Users
    },
    {
      label: 'Discord',
      href: `https://discord.gg/DChTnVTuKp`, // TODO: add this ssnkit/helpers or smth
      target: '_blank',
      icon: MessageCircle
    },
  ]
</script>

<nav class="navbar" aria-label="main navigation">
  <div class="container">
    <div class="navbar-brand">
      <button class="navbar-burger" aria-label="menu" aria-expanded="false">
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </button>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
      <div class="navbar-start">
        {#each navbarItems as item}
          <a
            href={item.href}
            target={item.target}
            class="navbar-item"
            class:is-active={item.href == currentUrl}
          >
            <svelte:component this={item.icon} size={24} />
            {item.label}
          </a>
        {/each}
      </div>

      <div class="navbar-end">
        <div class="navbar-item">
          {#if identity}
            <a href="{identityBaseUrl}/dashboard" class="user has-text-grey-dark">
              <img src="https://mc-heads.net/avatar/{identity.primaryUsername}/64" alt="Avatar de {identity.primaryUsername}" />
              <span>{identity.primaryUsername}</span>
            </a>
          {:else}
            <div class="buttons">
              <a href="{identityBaseUrl}/auth/register" class="button is-primary is-small">
                Registrar
              </a>
              <a href="{identityBaseUrl}/auth/login" class="button is-light is-small">
                Fazer login
              </a>
            </div>
          {/if}
        </div>
      </div>
    </div>
  </div>
</nav>

<style lang="sass">
  @import '../../../styles/vars'
  
  .navbar
    border-bottom: 1px solid #e6e7ea

    &-item
      gap: .25rem
      border-bottom: 2px solid transparent

      &.is-active
        color: $primary
        border-bottom-color: $primary

  .user
    display: flex
    align-items: center
    gap: .5rem

    img
      height: 28px
      width: 28px
      border-radius: .25rem
</style>