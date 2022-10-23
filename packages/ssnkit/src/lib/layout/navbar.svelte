<script lang="ts">
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
</script>

<nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="container">
    <div class="navbar-brand">
      <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
      <div class="navbar-start">
        <a class="navbar-item">
          Home
        </a>

        <a class="navbar-item">
          Documentation
        </a>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link">
            More
          </a>

          <div class="navbar-dropdown">
            <a class="navbar-item">
              About
            </a>
            <a class="navbar-item">
              Jobs
            </a>
            <a class="navbar-item">
              Contact
            </a>
            <hr class="navbar-divider">
            <a class="navbar-item">
              Report an issue
            </a>
          </div>
        </div>
      </div>

      <div class="navbar-end">
        <div class="navbar-item">
          {#if identity}
            <a href="/dashboard" class="user has-text-grey-dark">
              <img src="https://mc-heads.net/avatar/{identity.primaryUsername}/64" />
              <span>{identity.primaryUsername}</span>
            </a>
          {:else}
            <div class="buttons">
              <a href="/auth/register" class="button is-primary is-small">
                Registrar
              </a>
              <a href="/auth/login" class="button is-light is-small">
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
  .navbar
    border-bottom: 1px solid #e6e7ea

  .user
    display: flex
    align-items: center
    gap: .5rem

    img
      height: 28px
      width: 28px
      border-radius: .25rem
</style>