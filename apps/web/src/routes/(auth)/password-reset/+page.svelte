<script lang="ts">
  import * as Form from "$lib/components/ui/form";
  import { Input } from "$lib/components/ui/input";
  import { superForm, defaults } from "sveltekit-superforms";
  import { zod } from "sveltekit-superforms/adapters";
	import { z } from 'zod';
	import * as m from '$lib/paraglide/messages.js';
	import { pb } from '$lib/pocketbase';
	import { toast } from '$lib/utils';

  let loading = $state(false);
  let sentTo = $state<string | null>(null);

  const formSchema = z
    .object({
      email: z.string().email(m.invalid_email()),
    });
 
  const form = superForm(defaults(zod(formSchema)), {
    SPA: true,
    resetForm: false,
    validators: zod(formSchema),
    async onUpdate({ form }) {
      if (form.valid) {
        loading = true;

        try {
          await pb.collection('users').requestPasswordReset(form.data.email);
          sentTo = form.data.email;
        } catch (error) {
          toast.error(m.generic_error());
          return;
        }

        loading = false;
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
			<h1 class="text-2xl font-bold">{m.forgot_password()}</h1>
      {#if sentTo}
        <p class="text-foreground/80 [&_span]:text-foreground">
          {@html m.reset_password_page_sent({
            recipient: sentTo
          })}
        </p>
      {:else}
        <p class="text-foreground/80">
          {m.reset_password_page_text()}
        </p>
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
          <Form.Button class="w-fit" {loading}>
            {m.send_email()}
          </Form.Button>
        </form>
      {/if}
      <hr />
      <p class="text-muted-foreground text-sm">
        {m.not_registered_yet()}
        <a href="/register" class="underline-offset-2 underline text-foreground font-medium">
          {m.register()}
        </a>
      </p>
		</div>
	</div>
	<div class="bg-muted h-full hidden lg:block">
    <img src="/img/fllmall.webp" alt="" class="size-full object-cover" loading="lazy" style="user-select: none; user-drag: none" draggable="false">
  </div>
</div>
