<script lang="ts">
  export let label = 'ou';
</script>

<div class="separator">{label}</div>

<style lang="sass">
  .separator
    display: flex
    align-items: center
    gap: 1rem
    margin: 1.25rem 0

    &::before,
    &::after
      content: ''
      height: 1px
      flex-grow: 1
      background-color: #dbdbdb
</style>