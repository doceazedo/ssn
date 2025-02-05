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
	import type { RecordModel } from 'pocketbase';

  let loading = $state(false);

  const formSchema = z
    .object({
      email: z.string().email(m.invalid_email()),
      username: z
        .string()
        .min(2, m.invalid_username_length())
        .max(16, m.invalid_username_length())
        .regex(/^[a-zA-Z0-9_]{2,16}$/mg, m.invalid_username_regex()),
      password: z
        .string()
        .min(6, m.invalid_password_too_short())
        .max(72, m.invalid_password_too_long()),
      confirmPassword: z.string(),
    })
    .superRefine(({ confirmPassword, password }, ctx) => {
      if (confirmPassword !== password) {
        ctx.addIssue({
          code: "custom",
          message: m.invalid_password_confirmation(),
          path: ['confirmPassword']
        });
      }
    });
 
  const form = superForm(defaults(zod(formSchema)), {
    SPA: true,
    resetForm: false,
    validators: zod(formSchema),
    async onUpdate({ form }) {
      if (form.valid) {
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
          await pb.collection('users').create({
            email: form.data.email,
            password: form.data.password,
            passwordConfirm: form.data.password,
          });
        } catch (error) {
          loading = false;
          console.log(error);
          toast.error(m.register_error());
          return;
        }

        let user: RecordModel;
        try {
          const resp = await pb.collection('users').authWithPassword(
          form.data.email,
            form.data.password,
          );
          user = resp.record;
        } catch (error) {
          loading = false;
          toast.error(m.login_error());
          return;
        }

        try {
          await pb.collection('users').requestVerification(form.data.email);
        } catch (error) {}

        try {
          const username = await pb.collection('usernames').create({
            name: form.data.username,
            owner: user.id,
          });
          await pb.collection('users').update(user.id, {
            primaryUsername: username.id
          });
        } catch (error) {
          toast.error(m.register_primary_username_error());
        }

        goto('/account', { invalidateAll: true });
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
			<h1 class="text-2xl font-bold">{m.register_page_title()}</h1>
			<Button variant="discord" disabled={loading} onclick={loginWithDiscord}>
				<DiscordIcon class="size-5" />
				{m.register_with({ service: 'Discord' })}
			</Button>
			<div class="text-muted-foreground flex items-center justify-center gap-4 text-sm">
				<hr class="w-full" />
				{m.or()}
				<hr class="w-full" />
			</div>
      <form method="POST" use:enhance class="flex flex-col gap-4">
        <Form.Field {form} name="email">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>{m.email_address()}</Form.Label>
              <Input {...props} bind:value={$formData.email} disabled={loading} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
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
        <Form.Field {form} name="password">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>{m.password()}</Form.Label>
              <Input {...props} bind:value={$formData.password} type="password" disabled={loading} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
        <Form.Field {form} name="confirmPassword">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>{m.confirm_password()}</Form.Label>
              <Input {...props} bind:value={$formData.confirmPassword} type="password" disabled={loading} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
        <Form.Button class="w-fit" {loading}>{m.register_action ()}</Form.Button>
      </form>
      <hr />
      <p class="text-muted-foreground text-sm">
        {m.already_registered()}
        <a href="/login" class="underline-offset-2 underline text-foreground font-medium">
          {m.login()}
        </a>.
      </p>
		</div>
	</div>
	<div class="bg-muted h-full hidden lg:block">
    <img src="/img/fllmall.webp" alt="" class="size-full object-cover" loading="lazy" style="user-select: none; user-drag: none" draggable="false">
  </div>
</div>
