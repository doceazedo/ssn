<script lang="ts">
  import { Error } from "../components";

  export let code: number;

  const httpErrors: { [code: number]: string } = {
    400: "Requisição inválida",
    401: "Não autorizado",
    402: "Pagamento necessário",
    403: "Você não tem permissão para ver essa página",
    404: "Essa página não existe",
    405: "Método não permitido",
    406: "Requisição não aceitável",
    407: "Autenticação de proxy necessária",
    408: "Tempo esgotado",
    409: "Houve um conflito",
    410: "Essa página não existe mais",
    411: "Comprimento necessário",
    412: "Pré-condição falhou",
    413: "Dados da requisição grandes demais",
    414: "URI muito longa",
    415: "Tipo de mídia não suportado",
    416: "Faixa solicitada não satisfatória",
    417: "Quebra de expectativa 🤯",
    418: "Eu sou um bule de chá 🫖",
    420: "Relaxa aí... 😌💨",
    422: "Entidade improcessável",
    423: "Travado",
    424: "Falha de dependência",
    425: "Cedo demais 🛌",
    426: "Atualização obrigatória",
    428: "Pré-condição necessária",
    429: "Muitas solicitações",
    431: "Cabeçalhos de solicitação grandes demais",
    451: "Indisponível por motivos legais 👮‍♂️",
    500: "Erro interno do servidor",
    501: "Não implementado",
    502: "Falha de gateway",
    503: "Serviço indisponível 🐳",
    504: "Tempo do gateway esgotado",
    505: "Versão HTTP não suportada",
    506: "Variante também negocia",
    507: "Espaço de armazenamento insuficiente",
    508: "Loop detectado",
    509: "Limite de rede excedido",
    510: "Não estendido",
    511: "Autenticação de rede necessária",
    521: "Servidor web fechado",
    522: "Tempo de conexão esgotado",
    523: "Origem inalcançável",
    525: "Falha de SSL handshake",
    599: "Erro interno de rede",
  };

  $: errorMessage = httpErrors[code] || "Erro desconhecido";
</script>

<Error code="Erro {code}" message={errorMessage} />
