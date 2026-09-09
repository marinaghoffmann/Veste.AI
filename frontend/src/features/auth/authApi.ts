import { apiRequest } from "../../lib/apiClient";

export interface UsuarioResponse {
  id: number;
  nome: string;
  email: string;
}

export interface TokenResponse {
  token: string;
  tipo: string;
}

export function registrar(dados: { nome: string; email: string; senha: string }): Promise<UsuarioResponse> {
  return apiRequest<UsuarioResponse>("/auth/register", {
    method: "POST",
    body: JSON.stringify(dados),
  });
}

export function login(dados: { email: string; senha: string }): Promise<TokenResponse> {
  return apiRequest<TokenResponse>("/auth/login", {
    method: "POST",
    body: JSON.stringify(dados),
  });
}
