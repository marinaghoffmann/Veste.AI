export function validarNomePeca(nome: string): string | null {
  if (!nome.trim()) {
    return "Informe o nome da peça";
  }
  return null;
}

export function validarCategoria(categoria: string): string | null {
  if (!categoria) {
    return "Selecione uma categoria";
  }
  return null;
}

export function validarCor(cor: string): string | null {
  if (!cor.trim()) {
    return "Selecione ou informe uma cor";
  }
  return null;
}

export function validarEstacao(estacao: string): string | null {
  if (!estacao) {
    return "Selecione uma estação";
  }
  return null;
}
