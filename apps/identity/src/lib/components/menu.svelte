<script lang="ts">
  import { LayoutDashboard, Link, Users } from 'ssnkit/icons';
  import { page } from '$app/stores';

  $: items = [
    {
      label: 'Geral',
      list: [
        {
          label: 'Painel',
          href: '/dashboard',
          active: $page.url.pathname === '/dashboard',
          icon: LayoutDashboard,
        },
        {
          label: 'Minhas contas',
          href: '/dashboard/usernames',
          active: $page.url.pathname === '/dashboard/usernames',
          icon: Users,
        },
        {
          label: 'Conexões',
          href: '/dashboard/connections',
          active: $page.url.pathname === '/dashboard/connections',
          icon: Link,
        },
      ]
    }
  ]
</script>

<aside class="menu">
  {#each items as category}
    <p class="menu-label">
      {category.label}
    </p>
    <ul class="menu-list">
      {#each category.list as item}
        <li>
          <a class:is-active={item.active} href={item.href}>
            <svelte:component this={item.icon} size={16} />
            {item.label}
          </a>
        </li>
      {/each}
    </ul>
  {/each}
</aside>