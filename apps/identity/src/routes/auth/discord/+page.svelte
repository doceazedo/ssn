<script lang="ts">
  import { onMount } from "svelte";
  import { PageTitle } from "ssnkit";
  import { browser } from "$app/environment";

  export let data;

  let savedInvite: string;

  onMount(() => {
    if (!browser) return;
    savedInvite = localStorage.getItem("invite");
  });
</script>

<PageTitle pretitle="Registrar" title="Finalizar registro" />

<p>Quase lá! Quando você quiser fazer login no SSN.gg, você deverá utilizar essa conta do Discord:</p>
<div class="profile">
  <img class="avatar" src="https://cdn.discordapp.com/avatars/{data.user.id}/{data.user.avatar}.jpg" />
  <p class="user">
    <span class="name">{data.user.username}#{data.user.discriminator}</span>
    <a class="has-text-grey" href="#">Não é você?</a>
  </p>
</div>
<p>E para finalizar, escolha o nome de usuário que você deseja usar em jogo:</p>
<pre>{JSON.stringify(data, null, 2)}</pre>
<pre>savedInvite: {savedInvite}</pre>

<style lang="sass">
  .profile
    display: flex
    gap: 1rem

    .avatar
      height: 4rem
      border-radius: .75rem

    .user
      display: flex
      flex-direction: column
      justify-content: center

      .name
        font-size: 1.25rem
        font-weight: bold
</style>