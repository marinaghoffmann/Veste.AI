export const CHAVE_TOKEN = "vesteai.token";
export const CHAVE_EMAIL = "vesteai.usuarioEmail";

export function obterToken(): string | null {
  return localStorage.getItem(CHAVE_TOKEN);
}
