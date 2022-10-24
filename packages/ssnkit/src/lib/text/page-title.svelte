<script lang="ts">
  export let pretitle = '';
  export let title = '';

  let className = '';
  export { className as class };
</script>

<div class="page-title {className}">
  <div class="left">
    {#if !!pretitle.length}
      <p class="pretitle title is-5 has-text-grey mb-2 is-uppercase">{pretitle}</p>
    {/if}
    <h1 class="title">{title}</h1>
  </div>
  <div class="right">
    <slot />
  </div>
</div>

<style lang="sass">
  .page-title
    display: flex
    justify-content: space-between
    align-items: center
    width: 100%

    .left
      letter-spacing: .75px

    .right
      display: flex
</style>
