<script lang="ts">
  import 'ssnkit/styles';
  import ssnIcon from 'ssnkit/assets/img/ssn-icon.png';
  import { page } from '$app/stores';
  import { Metadata } from 'ssnkit';
</script>

<Metadata appTitle="SSN Gatekeeper" currentUrl={$page.url.href} />

<div class="container">
  <section class="section">
    <header class="header">
      <img src={ssnIcon} />
    </header>
    <div class="card">
      <div class="card-content">
        <div class="content">
          <slot />
        </div>
      </div>
    </div>
  </section>
</div>

<style lang="sass">
  .container
    max-width: 800px !important

  .header
    display: flex
    justify-content: center
    margin-bottom: 2rem

    img
      height: 6rem
</style>