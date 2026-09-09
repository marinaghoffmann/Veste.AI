const BASE_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

export class ApiError extends Error {
  status: number;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
  }
}

export async function apiRequest<T>(path: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
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
