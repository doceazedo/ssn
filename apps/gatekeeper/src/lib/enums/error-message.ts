export const errorMessage = {
  UNAUTHORIZED: 'A sua sessão expirou ou é inválida, tente fazer login novamente ou trocar de conta',
  FORBIDDEN: 'Você não é o proprietário desse usuário. Se acredita no contrário, tente trocar de conta.',
  INVALID_CODE: 'O código deve ter cinco dígitos alfanuméricos',
  INVALID_TTL: 'Duração da permissão inválida',
  EXPIRED_CODE: 'O código informado não existe ou expirou',
  GRANTED_FLOW: 'A permissão de acesso já foi concedida à esse fluxo',
  GRANTED_USERNAME: 'Esse IP já está autorizado a jogar com esse nome de usuário',
  GRANT_FAIL: 'Não foi possível realizar a autorização, tente novamente mais tarde',
  FLOW_FAIL: 'Não foi possível atualizar o fluxo de autorização, tente novamente mais tarde',
};