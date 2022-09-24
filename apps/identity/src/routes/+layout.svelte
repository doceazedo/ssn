<script lang="ts">
  import 'ssnkit/styles';
</script>

<div class="split">
  <main class="main">
    <header class="header">
      <a href="/" class="logo">
        <img src="/img/ssn-icon.png" alt="" />
      </a>
    </header>
    <slot />
  </main>
  <aside class="featured-image" />
</div>

<style lang="sass">
  :global(html, body)
    height: 100%

  .split
    display: flex
    width: 100%
    height: 100%

    .featured-image
      flex-grow: 1
      height: 100%
      background-image: url('/img/fllmall.webp')
      background-position: center
      background-repeat: no-repeat
      background-size: cover

    .main
      display: flex
      flex-direction: column
      gap: 2rem
      flex-shrink: 0
      width: 100%
      max-width: 36rem
      height: 100%
      padding: 2rem
      overflow-y: auto

      .logo
        display: flex
        width: fit-content

        img
          height: 5rem
          pointer-events: none
          user-select: none
</style>