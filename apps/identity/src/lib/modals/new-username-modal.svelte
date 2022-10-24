<script lang="ts">
  import { Input } from 'ssnkit';
  import { fade, scale, slide } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';
  import { createNewUsername } from "$lib/auth";

  export let isOpen = false;

  let username = '';
  let isLoading = false;
  let error = '';

  $: isOpen, error = '';

  const close = () => {
    if (!isLoading) isOpen = false;
  };

  const handleSubmit = async () => {
    if (isLoading) return;
    try {
      isLoading = true;
      error = '';

      if (!username.length) throw Error('Digite um nome de usuário');
      await createNewUsername(username);
    } catch (e) {
      isLoading = false;
      error = e.message;
    }
  };
</script>

{#if isOpen}
  <div class="modal is-active" transition:fade={{ duration: 200, easing: quintOut }}>
    <div class="modal-background" on:click={close}></div>
    <div class="modal-card" transition:scale={{ duration: 200, opacity: 1, start: .75, easing: quintOut }}>
      <header class="modal-card-head">
        <p class="modal-card-title">Criar novo usuário</p>
        <button class="delete" aria-label="close" on:click={close} />
      </header>
      <section class="modal-card-body">
        <div class="content">
          <p class="mb-5">Você está criando um novo usuário, você poderá acessá-lo com a mesma senha ou método de login que você já definiu anteriormente.</p>
          <Input bind:value={username} disabled={isLoading} name="username" label="Nome de usuário" />
          {#if error}
            <div
              class="notification is-danger is-light mt-4"
              transition:slide={{ duration: 200, easing: quintOut }}
            >
              {error}
            </div>
          {/if}
        </div>
      </section>
      <footer class="modal-card-foot">
        <button class="button is-primary" class:is-loading={isLoading} on:click={handleSubmit}>Criar</button>
        <button class="button" disabled={isLoading} on:click={close}>Cancelar</button>
      </footer>
    </div>
  </div>
{/if}