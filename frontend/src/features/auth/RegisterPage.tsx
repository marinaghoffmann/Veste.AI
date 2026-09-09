import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "../../components/Button";
import { TextField } from "../../components/TextField";
import { ApiError } from "../../lib/apiClient";
import { registrar } from "./authApi";
import { validarEmail, validarNome, validarSenha } from "./validation";

export function RegisterPage() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");

  const [erroNome, setErroNome] = useState<string | null>(null);
  const [erroEmail, setErroEmail] = useState<string | null>(null);
  const [erroSenha, setErroSenha] = useState<string | null>(null);
  const [erroConfirmarSenha, setErroConfirmarSenha] = useState<string | null>(null);
  const [erroGeral, setErroGeral] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  async function aoEnviar(evento: FormEvent) {
    evento.preventDefault();
    setErroGeral(null);

    const mensagemErroNome = validarNome(nome);
    const mensagemErroEmail = validarEmail(email);
    const mensagemErroSenha = validarSenha(senha);
    const mensagemErroConfirmarSenha = confirmarSenha !== senha ? "As senhas não coincidem" : null;

    setErroNome(mensagemErroNome);
    setErroEmail(mensagemErroEmail);
    setErroSenha(mensagemErroSenha);
    setErroConfirmarSenha(mensagemErroConfirmarSenha);

    if (mensagemErroNome || mensagemErroEmail || mensagemErroSenha || mensagemErroConfirmarSenha) {
      return;
    }

    setEnviando(true);
    try {
      await registrar({ nome, email, senha });
      navigate("/login");
    } catch (erro) {
      setErroGeral(erro instanceof ApiError ? erro.message : "Não foi possível criar sua conta agora");
    } finally {
      setEnviando(false);
    }
  }

  return (
    <div style={{ maxWidth: "360px", margin: "0 auto" }}>
      <h2>Criar conta</h2>
      <form onSubmit={aoEnviar} style={{ display: "flex", flexDirection: "column", gap: "16px" }}>
        <TextField id="nome" label="Nome" value={nome} onChange={(e) => setNome(e.target.value)} erro={erroNome} />
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
        <TextField
          id="confirmarSenha"
          label="Confirmar senha"
          type="password"
          value={confirmarSenha}
          onChange={(e) => setConfirmarSenha(e.target.value)}
          erro={erroConfirmarSenha}
        />
        {erroGeral && <p style={{ color: "var(--color-danger)", margin: 0 }}>{erroGeral}</p>}
        <Button type="submit" disabled={enviando}>
          {enviando ? "Criando conta..." : "Criar conta"}
        </Button>
      </form>
      <p style={{ marginTop: "16px", fontSize: "var(--font-size-sm)" }}>
        Já tem conta? <Link to="/login">Entrar</Link>
      </p>
    </div>
  );
}
