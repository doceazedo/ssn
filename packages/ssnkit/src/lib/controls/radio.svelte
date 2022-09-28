<script lang="ts">
  export let label = '',
    checked = false,
    value = '',
    group: string = '';
</script>

<label class="radio-input" on:click>
  <input type="radio" {checked} class:checked bind:group bind:value />
  <span class="radio-button" />
  {label}
</label>

<style lang="sass">
  @import '../../../styles/vars'

  .radio-input
    position: relative
    display: flex
    align-items: center
    gap: .5rem
    cursor: pointer
    user-select: none

    .radio-button
      position: relative
      height: 1.25rem
      width: 1.25rem
      background-color: $primary
      border-radius: 50%
      transition: all .2s ease

      &::after
        content: ''
        position: absolute
        top: calc(50% - .25rem)
        left: calc(50% - .25rem)
        width: .5rem
        height: .5rem
        background: #fff
        border-radius: 50%
        transition: all .2s ease

    input
      position: absolute
      height: 0
      width: 0
      opacity: 0
      cursor: pointer

      &:not(.checked)
        ~ .radio-button
          background-color: #fff
          box-shadow: inset 0 0 0 1px rgba($muted-text, .2)

          &::after
            transform: scale(.25)
            opacity: 0

    &:hover input:not(.checked) ~ .radio-button
      background-color: #eee
</style>