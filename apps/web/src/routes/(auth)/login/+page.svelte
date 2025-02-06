<script lang="ts">
	import DiscordIcon from '$lib/components/icons/DiscordIcon.svelte';
	import { Button } from '$lib/components/ui/button';
  import * as Form from "$lib/components/ui/form";
  import { Input } from "$lib/components/ui/input";
  import { superForm, defaults } from "sveltekit-superforms";
  import { zod } from "sveltekit-superforms/adapters";
	import { loginWithDiscord } from '../utils';
	import { z } from 'zod';
	import * as m from '$lib/paraglide/messages.js';
	import { pb } from '$lib/pocketbase';
	import { goto } from '$app/navigation';
	import { toast } from '$lib/utils';
	import { i18n } from '$lib/i18n';
	import { page } from '$app/state';

  let loading = $state(false);

  const formSchema = z.object({
    usernameOrEmail: z
      .string()
      .email(m.invalid_email_or_username())
      .or(z
        .string()
        .regex(/^[a-zA-Z0-9_]{2,16}$/mg)
      ),
    password: z.string().nonempty(m.invalid_password())
  });
 
  const form = superForm(defaults(zod(formSchema)), {
    SPA: true,
    resetForm: false,
    validators: zod(formSchema),
    async onUpdate({ form }) {
      if (form.valid) {
        loading = true;

        const loginWithEmail = form.data.usernameOrEmail.includes('@');
        if (!loginWithEmail) {
          try {
            const username = await pb.collection('usernames').getFirstListItem(`name="${form.data.usernameOrEmail}"`);
            form.data.usernameOrEmail = username.id;
          } catch (_error) {}
        }

        try {
          await pb.collection('users').authWithPassword(
            form.data.usernameOrEmail,
            form.data.password,
          );
          const url = page.url.searchParams.get('redirect') || '/account';
          goto(i18n.resolveRoute(url), { invalidateAll: true });
        } catch (error) {
          loading = false;
          toast.error(m.login_error());
        }
      }
    }
  });
 
  const { form: formData, enhance } = form;
</script>

<div class="grid h-dvh w-dvw lg:grid-cols-2">
	<div class="flex h-dvh overflow-y-auto p-6 md:p-14 2xl:items-center 2xl:justify-center 2xl:p-0">
		<div class="flex w-full 2xl:max-w-md flex-col gap-8 h-max">
			<a class="w-fit" href="/">
				<img src="/img/ssn-icon.png" alt="" class="h-12" style="user-select: none; user-drag: none"
        draggable="false" />
			</a>
			<h1 class="text-2xl font-bold">{m.login_page_title()}</h1>
			<Button variant="discord" disabled={loading} onclick={() => loginWithDiscord(page.url.search)}>
				<DiscordIcon class="size-5" />
				{m.login_with({ service: 'Discord' })}
			</Button>
			<div class="text-muted-foreground flex items-center justify-center gap-4 text-sm">
				<hr class="w-full" />
				{m.or()}
				<hr class="w-full" />
			</div>
      <form method="POST" use:enhance class="flex flex-col gap-4">
        <Form.Field {form} name="usernameOrEmail">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>{m.email_or_username()}</Form.Label>
              <Input {...props} bind:value={$formData.usernameOrEmail} disabled={loading} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="password">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>{m.password()}</Form.Label>
              <Input {...props} bind:value={$formData.password} type="password" disabled={loading} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
        <div class="flex items-center gap-2">
          <Form.Button {loading}>{m.login()}</Form.Button>
          <Button href="/password-reset" variant="link" disabled={loading}>{m.forgot_password()}</Button>
        </div>
      </form>
      <hr />
      <p class="text-muted-foreground text-sm">
        {m.not_registered_yet()}
        <a href="/register{page.url.search}" class="underline-offset-2 underline text-foreground font-medium">
          {m.register()}
        </a>
      </p>
		</div>
	</div>
	<div class="bg-muted h-full hidden lg:block">
    <img src="/img/fllmall.webp" alt="" class="size-full object-cover" loading="lazy" style="user-select: none; user-drag: none" draggable="false">
  </div>
</div>
