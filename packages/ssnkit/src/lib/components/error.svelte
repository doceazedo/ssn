<script lang="ts">
  import { PageTitle } from "../text";
  import ReiChiquitaImg from "../../../assets/img/rei-chiquita.gif";

  export let code: string;
  export let message: string;
</script>

<div class="error">
  <img src={ReiChiquitaImg} alt="Rei Chiquita" />
  <PageTitle title={message} pretitle={code} class="mb-6" />
</div>

<style lang="sass">
  .error
    display: flex
    flex-direction: column
    align-items: center
    gap: 3rem
    width: 100%

    :global(.page-title)
      justify-content: center
      text-align: center
</style>
