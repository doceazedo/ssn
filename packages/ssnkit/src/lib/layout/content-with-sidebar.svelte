<script lang="ts">
  import { Sidebar } from '.';
  import type { JavaStatusResponse } from 'minecraft-server-util';

  export let status: JavaStatusResponse | null = null;
</script>

<div class="content-with-sidebar">
  <main class="main">
    <slot />
  </main>
  <aside class="sidebar">
    <Sidebar {status} />
  </aside>
</div>

<style lang="sass">
  .content-with-sidebar
    display: flex
    gap: 1.5rem

    .main,
    .sidebar
      display: flex
      flex-direction: column
      gap: 1.5rem

    .main
      width: 75%
      flex-shrink: 0

    .sidebar
      flex-grow: 1

  @media screen and (max-width: 768px)
    .content-with-sidebar
      flex-direction: column

      .main,
      .sidebar
        width: 100%
        flex-grow: 0
</style>
