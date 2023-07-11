<script lang="ts">
  export let checked = false;
</script>

<div class="control">
  <label class="checkbox">
    <input type="checkbox" bind:checked on:change />
    <slot />
  </label>
</div>

<style lang="sass">
  $highlight: #2d9902
  
  .checkbox
    display: inline-flex
    align-items: center
    gap: .5rem

    input
      width: 2.75rem
      height: 1.5rem
      appearance: none
      background-color: #f5f5f5
      border-radius: 0.25rem
      position: relative
      border: 1px solid #dbdbdb
      transition: all .2s ease

      &::before
        content: ''
        position: absolute
        width: 1rem
        height: 1rem
        background-color: $highlight
        top: 3px
        left: 3px
        border-radius: 0.25rem
        transition: all .2s ease

      &:checked
        background-color: $highlight
        border-color: $highlight

        &::before
          background-color: #fff
          left: 23px
</style>
