<script lang="ts">
  import { slide } from "svelte/transition";
  import { quintOut } from "svelte/easing";
  import {
    BookMarkedIcon,
    DiscordAltIcon,
    HeartHandshakeIcon,
    HomeIcon,
    ShieldCheckIcon,
  } from "../icons";
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
  export let websiteBaseUrl: string;
  export let identityBaseUrl: string;
  export let currentUrl = "";

  const navbarItems = [
    {
      label: "Início",
      href: websiteBaseUrl,
      icon: HomeIcon,
    },
    {
      label: "Apoiar",
      href: `${websiteBaseUrl}/donate`,
      icon: HeartHandshakeIcon,
    },
    {
      label: "Sobre o SSN",
      href: `${websiteBaseUrl}/about`,
      icon: BookMarkedIcon,
    },
    {
      label: "Segurança",
      href: `${websiteBaseUrl}/safety`,
      icon: ShieldCheckIcon,
    },
    {
      label: "Discord",
      href: `https://discord.gg/DChTnVTuKp`, // TODO: add this ssnkit/helpers or smth
      target: "_blank",
      icon: DiscordAltIcon,
    },
  ];

  let isOpen = false;
  let innerWidth: number;

  $: isMobile = innerWidth <= 1023;
</script>

<svelte:window bind:innerWidth />

<nav class="navbar" aria-label="main navigation">
  <div class="container">
    <div class="navbar-brand">
      <button
        class="navbar-burger"
        aria-label="menu"
        aria-expanded="false"
        on:click={() => (isOpen = !isOpen)}
      >
        <span aria-hidden="true" />
        <span aria-hidden="true" />
        <span aria-hidden="true" />
      </button>
    </div>

    {#if !isMobile || isOpen}
      <div
        class="navbar-menu is-active"
        transition:slide={{ duration: 300, easing: quintOut }}
      >
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
              <a
                href="{identityBaseUrl}/dashboard"
                class="user has-text-grey-dark"
              >
                <img
                  src="https://mc-heads.net/avatar/{identity.primaryUsername}/64"
                  alt="Avatar de {identity.primaryUsername}"
                />
                <span>{identity.primaryUsername}</span>
              </a>
            {:else}
              <div class="buttons">
                <a
                  href="{identityBaseUrl}/auth/register"
                  class="button is-primary is-small"
                >
                  Registrar
                </a>
                <a
                  href="{identityBaseUrl}/auth/login"
                  class="button is-light is-small"
                >
                  Fazer login
                </a>
              </div>
            {/if}
          </div>
        </div>
      </div>
    {/if}
  </div>
</nav>

<style lang="sass">
  @import '../../../styles/vars'
  
  .navbar
    border-bottom: $divider

    &-item
      gap: .25rem
      border-bottom: 2px solid transparent

      &.is-active
        color: $primary
        border-bottom-color: $primary

      :global(svg)
        width: 1.5rem
        height: 1.5rem

  .user
    display: flex
    align-items: center
    gap: .5rem

    img
      height: 28px
      width: 28px
      border-radius: .25rem

  @media screen and (max-width: 1023px)
    .navbar
      &-menu
        padding: 0

      &-item
        display: flex
        align-items: center
        gap: .5rem
        border-bottom: none
        border-left: 2px solid transparent

        &.is-active
          border-left-color: $primary

        :global(svg)
          width: 1.25rem
          height: 1.25rem

    .user img
      width: 1.25rem
      height: 1.25rem
</style>
