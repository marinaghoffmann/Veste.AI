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
