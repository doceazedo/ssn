<script lang="ts">
  export let pretitle = '',
    title = '';
</script>

<div class="page-title">
  {#if !!pretitle.length}
    <p class="pretitle title is-5 has-text-grey mb-2 is-uppercase">{pretitle}</p>
  {/if}
  <h1 class="title">{title}</h1>
</div>

<style lang="sass">
  .pretitle
    letter-spacing: .75px
</style>
