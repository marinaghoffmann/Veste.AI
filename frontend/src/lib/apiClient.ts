import { obterToken } from "./authStorage";

const BASE_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

export class ApiError extends Error {
  status: number;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
  }
}

export function resolverUrlArquivo(caminho: string): string {
  return caminho.startsWith("http") ? caminho : `${BASE_URL}${caminho}`;
}

export async function apiRequest<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = obterToken();
  const corpoEhFormData = options.body instanceof FormData;

  const response = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      ...(corpoEhFormData ? {} : { "Content-Type": "application/json" }),
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...options.headers,
    },
  });

  const corpo = await response.json().catch(() => null);

  if (!response.ok) {
    const mensagem = corpo?.mensagem ?? "Não foi possível completar a solicitação";
    throw new ApiError(response.status, mensagem);
  }

  return corpo as T;
}
