<script lang="ts">
  import { CopyIpButton } from "../elements";
  import witherHeroImg from "../../../assets/img/wither-hero.webp";
  import ssnIconImg from "../../../assets/img/ssn-icon.png";
  import { SERVER_STATUS } from "../stores";

  export let websiteBaseUrl: string;
</script>

<section
  class="hero section is-medium is-primary"
  style="background-image:url({witherHeroImg})"
>
  <div class="hero-body container">
    <div class="left">
      <a href={websiteBaseUrl}>
        <img class="logo" src={ssnIconImg} alt="Logotipo SSN" />
      </a>
    </div>
    <div class="right">
      <CopyIpButton playerCount={$SERVER_STATUS?.players.online || 0} />
    </div>
  </div>
</section>

<style lang="sass">
  @import '../../../styles/vars'
  .hero
    padding: 0
    background-position: center
    background-repeat: no-repeat
    background-size: cover

    &-body
      display: flex
      justify-content: space-between
      width: 100%
      padding: 4rem 0 !important

    .left,
    .right
      display: flex
      align-items: center

  .logo
    height: 8rem

  @media screen and (max-width: 1023px)
    .hero-body
      padding: 3rem 1.5rem !important

  @media screen and (max-width: 768px)
    .hero-body
      padding: 1.5rem !important

      .logo
        height: 5rem
</style>
