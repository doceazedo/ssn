<script lang="ts">
  import { onMount } from "svelte";
  import { browser } from "$app/environment";
  import { TermsModal } from '$lib/modals';
  import { registerWithDiscord } from "$lib/auth";
  import { Form } from '.';

  export let refreshToken: string;

  let invite: string;
  onMount(() => {
    if (!browser) return;
    invite = localStorage.getItem("invite");
  });

  let isTermsModalOpen = false;
  let acceptedTerms = false;

  let fields = [
    {
      label: 'Nome de usuário',
      name: 'username',
      value: ''
    }
  ];

  const links = [
    {
      label: 'Já possui uma conta?',
      href: '/auth/login'
    }
  ]

  let isLoading = false;
  let error = '';

  const handleSubmit = async () => {
    const username = fields.find(field => field.name == 'username').value;

    try {
      if (!username) throw Error('Escolha um nome de usuário');
      if (!refreshToken) throw Error('Houve um erro com seu token de acesso. Por favor, tente se registrar novamente');

      error = '';
      if (!acceptedTerms) return isTermsModalOpen = true;
      isLoading = true;

      await registerWithDiscord({ username, refreshToken }, invite);
    } catch (e) {
      isLoading = false;
      error = e.message;
    }
  }

  const handleAccept = () => {
    acceptedTerms = true;
    handleSubmit();
    isTermsModalOpen = false;
    acceptedTerms = false;
  }
</script>

<Form
  bind:fields
  on:submit={handleSubmit}
  {isLoading}
  {error}
  {links}
  submitLabel="Registrar"
/>

<TermsModal bind:isOpen={isTermsModalOpen} {handleAccept} />