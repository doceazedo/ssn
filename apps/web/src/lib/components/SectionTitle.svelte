<script lang="ts">
  let { children } = $props();
</script>

<h1 class="[&_svg]:size-8 [&_svg]:text-primary flex items-center w-full gap-8">
  <span class="flex items-center gap-2 text-2xl font-bold shrink-0">
    {@render children?.()}
  </span>
  <hr class="w-full" />
</h1>