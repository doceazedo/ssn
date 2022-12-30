<script lang="ts">
  import { fade, scale, slide } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';
	import { browser } from '$app/environment';
  import { updatePrimaryUsername } from '$lib/username';

  export let isOpen = false;
  export let username: string | undefined;

  let isLoading = false;
  let error = '';

  const close = () => {
    if (!isLoading) isOpen = false;
  };

  const handleConfirm = async () => {
    if (isLoading || !username) return;
    try {
      isLoading = true;
      await updatePrimaryUsername(username);
      if (browser) location.reload();
    } catch (e: any) {
      isLoading = false;
      error = e.message;
    }
  }
</script>

{#if isOpen}
  <div class="modal is-active" transition:fade={{ duration: 200, easing: quintOut }}>
    <div class="modal-background" on:click={close}></div>
    <div class="modal-card" transition:scale={{ duration: 200, opacity: 1, start: .75, easing: quintOut }}>
      <header class="modal-card-head">
        <p class="modal-card-title">Mudar conta principal</p>
        <button class="delete" aria-label="close" on:click={close} />
      </header>
      <section class="modal-card-body">
        <div class="content">
          <p>Tem certeza que quer tornar <b>{username}</b> sua conta principal? Esse nome de usuário será usado para se referir a você em nosso site e outras aplicações.</p>
          <p>Você pode mudar sua conta principal a qualquer momento.</p>
        </div>
        {#if error}
          <div
            class="notification is-danger is-light mt-4"
            transition:slide={{ duration: 200, easing: quintOut }}
          >
            {error}
          </div>
        {/if}
      </section>
      <footer class="modal-card-foot">
        <button class="button is-primary" class:is-loading={isLoading} on:click={handleConfirm}>Confirmar</button>
        <button class="button" disabled={isLoading} on:click={close}>Cancelar</button>
      </footer>
    </div>
  </div>
{/if}