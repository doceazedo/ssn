<script lang="ts">
  import { LayoutDashboard, Link, Mails, Server, Users } from 'ssnkit/icons';
  import { page } from '$app/stores';
  import { IDENTITY } from '$lib/auth';

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
    },
    {
      label: 'Administração',
      role: 'ADMIN',
      list: [
        {
          label: 'Servidor',
          href: '/dashboard/admin/server',
          active: $page.url.pathname === '/dashboard/admin/server',
          icon: Server,
        },
        {
          label: 'Convites',
          href: '/dashboard/admin/invites',
          active: $page.url.pathname === '/dashboard/admin/invites',
          icon: Mails,
        }
      ]
    }
  ]
</script>

<aside class="menu">
  {#each items as category}
    {#if !category.role || category.role === $IDENTITY?.role}
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
    {/if}
  {/each}
</aside>