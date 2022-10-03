<script lang="ts">
  import { CheckmarkIcon } from '../icons';

  export let checked = false;
</script>

<label class="checkbox-wrapper">
  <input type="checkbox" bind:checked />
  <span class="checkbox">
    <CheckmarkIcon />
  </span>
  <slot />
</label>

<style lang="sass">
  @import '../../../styles/vars'

  .checkbox-wrapper
    position: relative
    display: flex
    align-items: center
    gap: .5rem
    cursor: pointer

    input
      position: absolute
      height: 0
      width: 0
      opacity: 0

      &:checked + .checkbox
        background-color: $primary

      &:not(:checked) + .checkbox :global(svg)
        transform: scale(.25)
        opacity: 0

    .checkbox
      position: relative
      height: 1.25rem
      width: 1.25rem
      border-radius: .25rem
      box-shadow: inset 0 0 0 1px rgba($muted-text, .2)
      color: #fff

      &,
      :global(svg)
        transition: all .2s ease

    &:hover input:not(:checked) + .checkbox
      background-color: #eee
</style>