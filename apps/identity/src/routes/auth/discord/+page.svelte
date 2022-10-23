<script lang="ts">
  import { PageTitle } from "ssnkit";
  import { DiscordForm } from '$lib/forms';
  import { discordOauthUrl } from '$lib/helpers';

  export let data;
</script>

<PageTitle pretitle="Registrar" title="Finalizar registro" />

<p>Sempre que você precisar fazer login no SSN.gg, você poderá utilizar sua conta do Discord, por isso, a mantenha em segurança.</p>
<div class="profile">
  <img class="avatar" src="https://cdn.discordapp.com/avatars/{data.user.id}/{data.user.avatar}.jpg" />
  <p class="user">
    <span class="name">{data.user.username}#{data.user.discriminator}</span>
    <a class="has-text-grey" href={discordOauthUrl}>Não é você?</a>
  </p>
</div>
<p>Agora, escolha o nome de usuário que você deseja usar em jogo:</p>
<DiscordForm refreshToken={data.refreshToken} />

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