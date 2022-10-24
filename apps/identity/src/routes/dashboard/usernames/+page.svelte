<script lang="ts">
  import dayjs from 'dayjs';
  import relativeTime from 'dayjs/plugin/relativeTime';
  import 'dayjs/locale/pt-br';
  import { PageTitle } from 'ssnkit';
  import { Plus } from 'ssnkit/icons';
  import { IDENTITY } from "$lib/auth";
  import { NewUsernameModal } from '$lib/modals';

  export let data;

  let isNewUserModalOpen = false;

  const timeAgo = (date: Date) => {
    dayjs.extend(relativeTime);
    dayjs().locale('pt-br');
    return dayjs(date).fromNow();
  };
</script>

<PageTitle pretitle="Geral" title="Minhas contas" class="mb-6">
  <button class="button is-primary is-small" on:click={() => isNewUserModalOpen = true}>
    <span class="icon">
      <Plus />
    </span>
    <span>
      Adicionar nova
    </span>
  </button>
</PageTitle>

<div class="usernames">
  {#each data.usernames as username}
    <div class="card">
      <div class="card-content">
        <img class="avatar" src="https://mc-heads.net/head/{username.name}" />
        <div class="info">
          <h1 class="title is-5 mb-1">
            {username.name}
            {#if $IDENTITY.primaryUsername === username.name}
              <span class="tag is-primary">Principal</span>
            {/if}
          </h1>
          <p>
            {#if username.lastSeen}
              Visto por último {timeAgo(username.lastSeen)}
            {:else}
              <i>Ainda não se conectou</i>
            {/if}
          </p>
        </div>
        <div class="buttons ml-auto">
          <button class="button is-small">Tornar principal</button>
          <button class="button is-danger is-small">Excluir</button>
        </div>
      </div>
    </div>
  {/each}
</div>

<NewUsernameModal bind:isOpen={isNewUserModalOpen} />

<style lang="sass">
  .usernames
    display: flex
    flex-direction: column
    gap: 1.5rem

    .card-content
      display: flex
      align-items: center
      gap: 1.25rem

      .avatar
        height: 4rem
</style>