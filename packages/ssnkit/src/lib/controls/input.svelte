<script lang="ts">
  export let value = '',
    type = 'text',
    name: string,
    label: string,
    disabled = false;

  const inputType = (node: any) => {
    node.type = type;
  };
</script>

<div class="control" class:is-filled={!!value.length}>
  <label for={name}>{label}</label>
  <input class="input" bind:value use:inputType name="debug-{name}" {disabled} autocomplete="debug-{name}" />
</div>

<style lang="sass">
  @import '../../../styles/vars'

  .control
    label
      position: absolute
      top: .9rem
      left: 1rem
      padding: 0 .5rem
      background-color: #fff
      pointer-events: none
      transition: all .2s ease
      z-index: 10

    &:focus-within label,
    &.is-filled label
      top: -0.65rem
      font-size: .9rem

    &:focus-within label
      color: $primary

    .input
      height: 3.25rem
      padding: 0 1rem
      background-color: transparent
      box-shadow: none
      border-radius: .75rem
      transition: all .2s ease

      &:focus
        outline: none
        border-color: $primary
        box-shadow: none
</style>