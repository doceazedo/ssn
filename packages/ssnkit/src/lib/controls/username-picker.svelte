<script lang="ts">
  export let usernames: string[] = [];
  export let currentUsername: string;
</script>

<div class="control has-icons-left">
  <div class="select">
    <select bind:value={currentUsername}>
      {#each usernames as username}
        <option value={username}>{username}</option>
      {/each}
    </select>
  </div>
  <span class="icon is-left">
    <img src="https://mc-heads.net/avatar/{currentUsername}/28" alt="Avatar de {currentUsername}" />
  </span>
</div>

<style lang="sass">
  .control .select
    height: 3rem

    select
      height: 100%
      padding-left: 3rem
      border-radius: .75rem

  .icon
    height: 3rem
    width: 3rem

    img
      border-radius: .25rem
</style>