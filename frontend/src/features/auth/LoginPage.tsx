import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "../../components/Button";
import { TextField } from "../../components/TextField";
import { ApiError } from "../../lib/apiClient";
import { login } from "./authApi";
import { useAuth } from "./useAuth";
import { validarEmail, validarSenha } from "./validation";

export function LoginPage() {
  const navigate = useNavigate();
  const { entrar } = useAuth();

  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erroEmail, setErroEmail] = useState<string | null>(null);
  const [erroSenha, setErroSenha] = useState<string | null>(null);
  const [erroGeral, setErroGeral] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  async function aoEnviar(evento: FormEvent) {
    evento.preventDefault();
    setErroGeral(null);

    const mensagemErroEmail = validarEmail(email);
    const mensagemErroSenha = validarSenha(senha);
    setErroEmail(mensagemErroEmail);
    setErroSenha(mensagemErroSenha);

    if (mensagemErroEmail || mensagemErroSenha) {
      return;
    }

    setEnviando(true);
    try {
      const resposta = await login({ email, senha });
      entrar(resposta.token, email);
      navigate("/");
    } catch (erro) {
      setErroGeral(erro instanceof ApiError ? erro.message : "Não foi possível entrar agora");
    } finally {
      setEnviando(false);
    }
  }

  return (
    <div style={{ maxWidth: "360px", margin: "0 auto" }}>
      <h2>Entrar</h2>
      <form onSubmit={aoEnviar} style={{ display: "flex", flexDirection: "column", gap: "16px" }}>
        <TextField
          id="email"
          label="E-mail"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          erro={erroEmail}
        />
        <TextField
          id="senha"
          label="Senha"
          type="password"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          erro={erroSenha}
        />
        {erroGeral && <p style={{ color: "var(--color-danger)", margin: 0 }}>{erroGeral}</p>}
        <Button type="submit" disabled={enviando}>
          {enviando ? "Entrando..." : "Entrar"}
        </Button>
      </form>
      <p style={{ marginTop: "16px", fontSize: "var(--font-size-sm)" }}>
        Ainda não tem conta? <Link to="/registro">Criar conta</Link>
      </p>
    </div>
  );
}
