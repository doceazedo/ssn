<script lang="ts">
  import { page } from '$app/stores';
  import { register } from "$lib/auth";
  import { TermsModal } from '$lib/modals';
  import { Form } from '.';
  const invite = $page.url.searchParams.get('invite');

  let isTermsModalOpen = false;
  let acceptedTerms = false;

  let fields = [
    {
      label: 'Endereço de e-mail',
      name: 'email',
      value: ''
    },
    {
      label: 'Nome de usuário',
      name: 'username',
      value: ''
    },
    {
      label: 'Senha',
      name: 'password',
      type: 'password',
      value: ''
    },
    {
      label: 'Confirmar senha',
      name: 'confirm-password',
      type: 'password',
      value: ''
    },
  ];
  let captchaToken = '';

  const links = [
    {
      label: 'Já possui uma conta?',
      href: '/auth/login'
    }
  ]

  let isLoading = false;
  let error = '';

  const handleSubmit = async () => {
    const email = fields.find(field => field.name == 'email').value;
    const username = fields.find(field => field.name == 'username').value;
    const password = fields.find(field => field.name == 'password').value;
    const confirmPassword = fields.find(field => field.name == 'confirm-password').value;

    try {
      if (password != confirmPassword) throw Error('As senhas informadas não combinam');
      if (![email, username, password, confirmPassword].every(x => x))
        throw Error('Preencha todos os campos');

      error = '';

      if (!acceptedTerms) return isTermsModalOpen = true;

      isLoading = true;

      await register({
        email,
        username,
        password,
        captchaToken
      }, invite);
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
  hasCaptcha
  discordLabel="Registrar com Discord"
  submitLabel="Registrar"
  bind:captchaToken
/>

<TermsModal bind:isOpen={isTermsModalOpen} {handleAccept} />