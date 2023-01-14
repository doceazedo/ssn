<script lang="ts">
	import { onMount } from 'svelte';
  import { ChevronLeft, ChevronRight } from 'ssnkit/icons';
  import images from './assets';
  import Glide, { Autoplay, Controls, Swipe } from '@glidejs/glide/dist/glide.modular.esm';
  import '@glidejs/glide/dist/css/glide.core.min.css';

  onMount(() => {
    new Glide('.glide', {
      gap: 0
    })
      .mount({ Autoplay, Controls, Swipe })
      .play(4000);
  });
</script>

<div class="glide">
  <div class="glide__track" data-glide-el="track">
    <ul class="glide__slides">
      {#each images as image}
        <li class="glide__slide" style="background-image: url({image})"></li>
      {/each}
    </ul>
    <div class="glide__arrows" data-glide-el="controls">
      <button class="glide__arrow glide__arrow--left" data-glide-dir="<">
        <ChevronLeft size={24} />
      </button>
      <button class="glide__arrow glide__arrow--right" data-glide-dir=">">
        <ChevronRight size={24} />
      </button>
    </div>
  </div>
</div>

<style lang="sass">
  .glide
    aspect-ratio: 21 / 9
    background-color: #e5e7ea
    border-radius: 1rem
    overflow: hidden

    &__track,
    &__slides
      height: 100%

    &__slide
      background-position: center
      background-repeat: no-repeat
      background-size: cover

    &__arrows
      display: flex
      justify-content: space-between
      width: 100%
      position: absolute
      top: calc(50% - 1rem)
      padding: 0 1.5rem
      transition: all .2s ease

    &:not(:hover) &__arrows
      opacity: 0
      padding: 0 .75rem

    &__arrow
      display: flex
      justify-content: center
      align-items: center
      height: 2rem
      width: 2rem
      border-radius: 50%
      border: none
      background-color: #2c9902
      color: #fff
      cursor: pointer

  @media screen and (max-width: 768px)
    .glide
      aspect-ratio: 16 / 9
</style>
