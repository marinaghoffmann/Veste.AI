import { createContext, useMemo, useState, type ReactNode } from "react";

const CHAVE_TOKEN = "vesteai.token";
const CHAVE_EMAIL = "vesteai.usuarioEmail";

export interface AuthContextValue {
  token: string | null;
  usuarioEmail: string | null;
  entrar: (token: string, usuarioEmail: string) => void;
  sair: () => void;
}

export const AuthContext = createContext<AuthContextValue | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem(CHAVE_TOKEN));
  const [usuarioEmail, setUsuarioEmail] = useState<string | null>(() => localStorage.getItem(CHAVE_EMAIL));

  const value = useMemo<AuthContextValue>(
    () => ({
      token,
      usuarioEmail,
      entrar: (novoToken, novoEmail) => {
        localStorage.setItem(CHAVE_TOKEN, novoToken);
        localStorage.setItem(CHAVE_EMAIL, novoEmail);
        setToken(novoToken);
        setUsuarioEmail(novoEmail);
      },
      sair: () => {
        localStorage.removeItem(CHAVE_TOKEN);
        localStorage.removeItem(CHAVE_EMAIL);
        setToken(null);
        setUsuarioEmail(null);
      },
    }),
    [token, usuarioEmail],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
