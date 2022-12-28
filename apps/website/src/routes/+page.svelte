<script lang="ts">
  import { ContentWithSidebar, Card } from "ssnkit";
  import { Hand, LifeBuoy, Rocket, Swords } from "ssnkit/icons";
</script>

<ContentWithSidebar>
  <div class="gallery-placeholder"></div>

  <Card title="Conheça o SSN.gg!" icon={Hand}>
    <div class="content">
      <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Tenetur recusandae maxime at accusamus quidem. Porro, soluta. Veritatis autem, impedit saepe ex enim atque itaque ea, tempora laborum sunt repellat quas.</p>
    </div>
  </Card>

  <Card title="Como conectar" icon={LifeBuoy} id="tutorial">
    <div class="content">
      <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Tenetur recusandae maxime at accusamus quidem. Porro, soluta. Veritatis autem, impedit saepe ex enim atque itaque ea, tempora laborum sunt repellat quas.</p>
    </div>
  </Card>

  <Card title="Primeiros passos" icon={Rocket}>
    <div class="content">
      <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Tenetur recusandae maxime at accusamus quidem. Porro, soluta. Veritatis autem, impedit saepe ex enim atque itaque ea, tempora laborum sunt repellat quas.</p>
    </div>
  </Card>

  <Card title="Mods utilitários" icon={Swords} id="clients">
    <div class="content">
      <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Tenetur recusandae maxime at accusamus quidem. Porro, soluta. Veritatis autem, impedit saepe ex enim atque itaque ea, tempora laborum sunt repellat quas.</p>
    </div>
  </Card>
</ContentWithSidebar>

<style lang="sass">
  .gallery-placeholder
    aspect-ratio: 21 / 9
    background-color: #e5e7ea
    border-radius: 1rem
</style>