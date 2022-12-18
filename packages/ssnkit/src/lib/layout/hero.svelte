<script lang="ts">
  import witherImg from '../../../assets/img/wither.jpeg';
  import ssnIconImg from '../../../assets/img/ssn-icon.png';

  export let websiteBaseUrl: string;
</script>

<section class="hero is-medium is-primary" style="background-image:url({witherImg})">
  <div class="hero-body">
    <div class="left">
      <a href={websiteBaseUrl}>
        <img class="logo" src={ssnIconImg} alt="Logotipo SSN" />
      </a>
    </div>
    <div class="right"></div>
  </div>
</section>

<style lang="sass">
  @import '../../../styles/vars'
  .hero
    background-position: center
    background-repeat: no-repeat
    background-size: cover

    &-body
      display: flex
      background-color: rgba($primary, .75)

  .logo
    height: 8rem

  @media screen and (max-width: 768px)
    .hero-body
      justify-content: center
      padding: 1.5rem !important

      .logo
        height: 6rem

  @media screen and (min-width: 769px)
    .hero-body
      padding: 4rem 4.5rem !important
</style>