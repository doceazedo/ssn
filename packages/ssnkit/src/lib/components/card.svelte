<script lang="ts">
  import type { SvelteComponent } from 'svelte';

  export let title = "";
  export let icon: typeof SvelteComponent = null;
  export let id: string = null;
</script>

<div class="card" {id}>
  {#if title}
    <header class="card-header">
      <p class="card-header-title">
        {#if icon}
          <svelte:component this={icon} size={24} width={24} height={24} />
        {/if}
        {title}
      </p>
    </header>
  {/if}
  <div class="card-content">
    <slot />
  </div>
</div>