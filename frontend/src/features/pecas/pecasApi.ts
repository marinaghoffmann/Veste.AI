import { apiRequest } from "../../lib/apiClient";

export interface PecaResponse {
  id: number;
  nome: string;
  categoria: string;
  cor: string;
  estacao: string;
  fotoUrl: string | null;
  disponivel: boolean;
}

export interface FotoResponse {
  fotoUrl: string;
  // key do objeto no S3 (ex: "pecas/1.png"), usada pra consultar o
  // resultado da IA. Vem null se o upload pro S3 falhou no backend —
  // nesse caso não tem o que consultar, e o usuário preenche
  // categoria/cor manualmente.
  iaKey: string | null;
}

export interface IaPreview {
  categoria: string;
  cor: string;
  confianca: number;
}

export function enviarFoto(arquivo: File): Promise<FotoResponse> {
  const formData = new FormData();
  formData.append("arquivo", arquivo);
  return apiRequest<FotoResponse>("/api/pecas/fotos", {
    method: "POST",
    body: formData,
  });
}

export function cadastrarPeca(dados: {
  nome: string;
  categoria: string;
  cor: string;
  estacao: string;
  fotoUrl: string;
}): Promise<PecaResponse> {
  return apiRequest<PecaResponse>("/api/pecas", {
    method: "POST",
    body: JSON.stringify(dados),
  });
}

/**
 * Consulta uma vez o resultado da IA pra uma peça. O backend responde
 * 204 (sem corpo) enquanto a Lambda ainda não processou — o apiRequest
 * trata isso como sucesso com corpo null, então aqui simplesmente
 * retornamos o que ele der: null (ainda não tem) ou o IaPreview (já tem).
 */
function consultarIaPreviewUmaVez(iaKey: string): Promise<IaPreview | null> {
  return apiRequest<IaPreview | null>(`/api/pecas/ia-preview?key=${encodeURIComponent(iaKey)}`);
}

/**
 * Fica consultando o resultado da IA de tempos em tempos, até aparecer
 * ou até estourar o número de tentativas. Retorna null nesse último caso
 * (não é considerado erro — o usuário sempre pode preencher na mão).
 */
export async function aguardarIaPreview(
  iaKey: string,
  tentativas = 10,
  intervaloMs = 1500,
): Promise<IaPreview | null> {
  for (let i = 0; i < tentativas; i++) {
    const resultado = await consultarIaPreviewUmaVez(iaKey);
    if (resultado) {
      return resultado;
    }
    await new Promise((resolve) => setTimeout(resolve, intervaloMs));
  }
  return null;
}
