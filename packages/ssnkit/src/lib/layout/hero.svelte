<script lang="ts">
  import witherImg from '../../../assets/img/wither.jpeg';
  import ssnIconImg from '../../../assets/img/ssn-icon.png';
</script>

<section class="hero is-medium is-primary" style="background-image:url({witherImg})">
  <div class="hero-body">
    <div class="left">
      <img class="logo" src={ssnIconImg} />
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
      background-color: rgba($primary, .75)

  .logo
    height: 8rem

  @media screen and (min-width: 769px)
    .hero-body
      padding: 4rem 4.5rem !important
</style>