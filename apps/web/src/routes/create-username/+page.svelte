<script lang="ts">
  import * as Form from "$lib/components/ui/form";
  import { Input } from "$lib/components/ui/input";
  import { superForm, defaults } from "sveltekit-superforms";
  import { zod } from "sveltekit-superforms/adapters";
	import { z } from 'zod';
	import * as m from '$lib/paraglide/messages.js';
	import { pb } from '$lib/pocketbase';
	import { goto } from '$app/navigation';
	import { toast } from '$lib/utils';
	import type { RecordModel } from 'pocketbase';
	import { AUTH_DATA, PRIMARY_USERNAME, USER } from '$lib/stores';

  let loading = $state(false);

  const formSchema = z
    .object({
      username: z
        .string()
        .min(2, m.invalid_username_length())
        .max(16, m.invalid_username_length())
        .regex(/^[a-zA-Z0-9_]{2,16}$/mg, m.invalid_username_regex()),
    });
 
  const form = superForm(defaults(zod(formSchema)), {
    SPA: true,
    resetForm: false,
    validators: zod(formSchema),
    async onUpdate({ form }) {
      if (form.valid) {
        if (!$USER) return;
        loading = true;

        try {
          await pb.collection('usernames').getFirstListItem(`name="${form.data.username}"`);
          loading = false;
          form.errors.username = [
            m.username_taken()
          ];
          return;
        } catch (_error) {}

        try {
          const username = await pb.collection('usernames').create({
            name: form.data.username,
            owner: $USER.id,
          });
          $PRIMARY_USERNAME = username;
          await pb.collection('users').update($USER.id, {
            primaryUsername: username.id
          });
        } catch (error) {
          loading = false;
          toast.error(m.register_primary_username_error());
          return;
        }

        goto('/account', { invalidateAll: true });
      }
    }
  });

  const getDiscordAvatar = (url: string) => {
    if (!url) return '';
    const fileName = url.split('/')?.[5] || '';
    if (fileName.startsWith('a_')) {
      return url.replace('.png', '.gif');
    }
    return url;
  }
 
  const { form: formData, enhance } = form;
</script>

<div class="grid h-dvh w-dvw lg:grid-cols-2">
	<div class="flex h-dvh overflow-y-auto p-6 md:p-14 2xl:items-center 2xl:justify-center 2xl:p-0">
		<div class="flex w-full 2xl:max-w-md flex-col gap-8 h-max">
			<a class="w-fit" href="/">
				<img src="/img/ssn-icon.png" alt="" class="h-12" style="user-select: none; user-drag: none"
        draggable="false" />
			</a>
			<h1 class="text-2xl font-bold">{m.create_username_page_title()}</h1>
      {#if $AUTH_DATA?.meta}
        <div class="flex gap-3 items-center">
          <img src={getDiscordAvatar($AUTH_DATA.meta.avatarUrl)} alt="" class="bg-muted size-14 rounded-lg object-cover" />
          <div class="flex flex-col leading-snug">
            <p>{$AUTH_DATA.meta.rawUser.global_name || $AUTH_DATA.meta.rawUser.username}</p>
            <p class="text-xs text-muted-foreground">
              {m.connected_with({ service: 'Discord' })}
            </p>
          </div>
        </div>
      {/if}
      <p class="text-foreground/80">
        {m.create_username_page_text()}
      </p>
      <form method="POST" use:enhance class="flex flex-col gap-4">
        <Form.Field {form} name="username">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>{m.username()}</Form.Label>
              <Input {...props} bind:value={$formData.username} disabled={loading} />
            {/snippet}
          </Form.Control>
          <Form.Description>
            {m.username_help()}
          </Form.Description>
          <Form.FieldErrors />
        </Form.Field>
        <Form.Button class="w-fit" {loading}>
          {m.confirm()}
        </Form.Button>
      </form>
		</div>
	</div>
	<div class="bg-muted h-full hidden lg:block">
    <img src="/img/fllmall.webp" alt="" class="size-full object-cover" loading="lazy" style="user-select: none; user-drag: none" draggable="false">
  </div>
</div>
