<script lang="ts">
  export let reverse = false;
</script>

<div class="content-with-sidebar" class:is-reverse={reverse}>
  <main class="main">
    <slot name="main" />
  </main>
  <aside class="sidebar">
    <slot name="aside" />
  </aside>
</div>

<style lang="sass">
  .content-with-sidebar
    display: flex
    gap: 1.5rem

    &.is-reverse
      flex-direction: row-reverse

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
