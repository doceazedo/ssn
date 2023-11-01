<script lang="ts">
  import { ContentWithAside, Sidebar } from ".";
</script>

<ContentWithAside>
  <slot slot="main" />
  <svelte:fragment slot="aside">
    <Sidebar />
  </svelte:fragment>
</ContentWithAside>

<div class="content-with-sidebar">
  <main class="main" />
  <aside class="sidebar" />
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
      width: 0
      flex-grow: 1

    .sidebar
      width: 20rem
      flex-shrink: 0

  @media screen and (max-width: 768px)
    .content-with-sidebar
      flex-direction: column

      .main,
      .sidebar
        width: 100%
        flex-grow: 0
</style>
