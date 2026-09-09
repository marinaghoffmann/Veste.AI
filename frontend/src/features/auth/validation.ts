const FORMATO_EMAIL = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
const TAMANHO_MINIMO_SENHA = 8;

export function validarEmail(email: string): string | null {
  if (!email.trim()) {
    return "Informe seu e-mail";
  }
  if (!FORMATO_EMAIL.test(email)) {
    return "Informe um e-mail válido";
  }
  return null;
}

export function validarSenha(senha: string): string | null {
  if (!senha) {
    return "Informe sua senha";
  }
  if (senha.length < TAMANHO_MINIMO_SENHA) {
    return `A senha deve ter ao menos ${TAMANHO_MINIMO_SENHA} caracteres`;
  }
  return null;
}

export function validarNome(nome: string): string | null {
  if (!nome.trim()) {
    return "Informe seu nome";
  }
  return null;
}
