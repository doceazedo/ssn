<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { IDENTITY, logout } from "$lib/auth";
  import { identityUrl, inviteOnly } from '$lib/env/public';

  export let data;

  $: usernameS = $IDENTITY.usernames.length > 1 ? 's' : '';
  $: inviteS = data.invites.length > 1 ? 's' : '';
  $: availableS = data.invites.length > 1 ? 'disponíveis' : 'disponível';
</script>

<PageTitle pretitle="Painel" title={$IDENTITY?.primaryUsername} class="mb-6" />

<div class="notification is-warning is-light">
  Você tem <b>{$IDENTITY.usernames.length} usuário{usernameS}</b> cadastrado{usernameS}.
  Para criar mais um, acesse <a href="/dashboard/usernames">Minhas contas</a>.
</div>

{#if inviteOnly}
  <h1 class="title is-4">Meus convites</h1>
  <div class="card">
    <div class="card-content">
      <p>
        Você tem <b>{data.invites.length} convite{inviteS} {availableS}</b>. Envie um link para quem você quiser que participe do teste fechado. Qualquer pessoa com um desses links poderá usá-los, por isso, os mantenha em segurança.
      </p>

      {#if data.invites.length}
        <ul class="invites">
          {#each data.invites as invite}
            <li>
              <span>{identityUrl}/invite/{invite.code}</span>
            </li>
          {/each}
        </ul>
      {/if}

      {#if data.invitedUsers.length}
        <div class="notification is-info is-light mt-4">
          Usuários que se cadastraram usando seus convites:
          {#each data.invitedUsers as username, i}
            <b>{username}</b>{i < data.invitedUsers.length - 1 ? ', ' : ' '}
          {/each}
        </div>
      {/if}
    </div>
  </div>
{/if}

<button class="button is-danger is-small mt-6" on:click={logout}>
  Desconectar
</button>

<style lang="sass">
  .invites
    display: flex
    flex-direction: column
    gap: .5rem
    margin-top: 1.5rem

    li
      padding: .75rem
      border: 2px dashed #ddd
      border-radius: .5rem

      span
        transition: all .2s ease

        &:not(:hover)
          filter: blur(.25rem)
</style>