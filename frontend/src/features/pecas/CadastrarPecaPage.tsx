import { useRef, useState, type ChangeEvent, type FormEvent } from "react";
import { Link } from "react-router-dom";
import { Button } from "../../components/Button";
import { SelectField } from "../../components/SelectField";
import { TextField } from "../../components/TextField";
import { ApiError, resolverUrlArquivo } from "../../lib/apiClient";
import { CATEGORIAS, CORES_SUGERIDAS, ESTACOES } from "./opcoes";
import { cadastrarPeca, enviarFoto, type PecaResponse } from "./pecasApi";
import { validarCategoria, validarCor, validarEstacao, validarNomePeca } from "./validation";

export function CadastrarPecaPage() {
  const inputFotoRef = useRef<HTMLInputElement>(null);

  const [nome, setNome] = useState("");
  const [categoria, setCategoria] = useState("");
  const [cor, setCor] = useState("");
  const [estacao, setEstacao] = useState("");
  const [fotoUrl, setFotoUrl] = useState<string | null>(null);

  const [erroNome, setErroNome] = useState<string | null>(null);
  const [erroCategoria, setErroCategoria] = useState<string | null>(null);
  const [erroCor, setErroCor] = useState<string | null>(null);
  const [erroEstacao, setErroEstacao] = useState<string | null>(null);
  const [erroFoto, setErroFoto] = useState<string | null>(null);
  const [erroGeral, setErroGeral] = useState<string | null>(null);

  const [enviandoFoto, setEnviandoFoto] = useState(false);
  const [enviando, setEnviando] = useState(false);
  const [pecaCriada, setPecaCriada] = useState<PecaResponse | null>(null);

  async function aoSelecionarFoto(evento: ChangeEvent<HTMLInputElement>) {
    const arquivo = evento.target.files?.[0];
    if (!arquivo) {
      return;
    }

    setErroFoto(null);
    setErroGeral(null);
    setEnviandoFoto(true);
    try {
      const resposta = await enviarFoto(arquivo);
      setFotoUrl(resposta.fotoUrl);
    } catch (erro) {
      setFotoUrl(null);
      setErroFoto(erro instanceof ApiError ? erro.message : "Não foi possível enviar a foto");
    } finally {
      setEnviandoFoto(false);
    }
  }

  function limparFormulario() {
    setNome("");
    setCategoria("");
    setCor("");
    setEstacao("");
    setFotoUrl(null);
    if (inputFotoRef.current) {
      inputFotoRef.current.value = "";
    }
  }

  async function aoEnviar(evento: FormEvent) {
    evento.preventDefault();
    setErroGeral(null);

    const mensagemErroNome = validarNomePeca(nome);
    const mensagemErroCategoria = validarCategoria(categoria);
    const mensagemErroCor = validarCor(cor);
    const mensagemErroEstacao = validarEstacao(estacao);
    const mensagemErroFoto = fotoUrl ? null : "Envie uma foto da peça";

    setErroNome(mensagemErroNome);
    setErroCategoria(mensagemErroCategoria);
    setErroCor(mensagemErroCor);
    setErroEstacao(mensagemErroEstacao);
    setErroFoto(mensagemErroFoto);

    if (mensagemErroNome || mensagemErroCategoria || mensagemErroCor || mensagemErroEstacao || mensagemErroFoto) {
      return;
    }

    setEnviando(true);
    try {
      const peca = await cadastrarPeca({ nome, categoria, cor, estacao, fotoUrl: fotoUrl! });
      setPecaCriada(peca);
      limparFormulario();
    } catch (erro) {
      setErroGeral(erro instanceof ApiError ? erro.message : "Não foi possível cadastrar a peça agora");
    } finally {
      setEnviando(false);
    }
  }

  if (pecaCriada) {
    return (
      <div style={{ maxWidth: "400px", margin: "0 auto" }}>
        <h2>Peça cadastrada!</h2>
        {pecaCriada.fotoUrl && (
          <img
            src={resolverUrlArquivo(pecaCriada.fotoUrl)}
            alt={pecaCriada.nome}
            style={{ width: "160px", height: "160px", objectFit: "cover", borderRadius: "8px" }}
          />
        )}
        <p>
          <strong>{pecaCriada.nome}</strong>
        </p>
        <div style={{ display: "flex", gap: "12px" }}>
          <Button onClick={() => setPecaCriada(null)}>Cadastrar outra peça</Button>
          <Link to="/">Voltar para o início</Link>
        </div>
      </div>
    );
  }

  return (
    <div style={{ maxWidth: "400px", margin: "0 auto" }}>
      <h2>Cadastrar peça</h2>
      <form onSubmit={aoEnviar} style={{ display: "flex", flexDirection: "column", gap: "16px" }}>
        <TextField id="nome" label="Nome" value={nome} onChange={(e) => setNome(e.target.value)} erro={erroNome} />

        <SelectField
          id="categoria"
          label="Categoria"
          opcoes={CATEGORIAS}
          value={categoria}
          onChange={(e) => setCategoria(e.target.value)}
          erro={erroCategoria}
        />

        <div style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
          <span style={{ fontSize: "var(--font-size-sm)", fontWeight: 600 }}>Cor</span>
          <div style={{ display: "flex", flexWrap: "wrap", gap: "8px" }}>
            {CORES_SUGERIDAS.map((sugestao) => (
              <button
                key={sugestao.valor}
                type="button"
                title={sugestao.valor}
                onClick={() => setCor(sugestao.valor)}
                style={{
                  width: "28px",
                  height: "28px",
                  borderRadius: "50%",
                  background: sugestao.cor,
                  border:
                    cor === sugestao.valor
                      ? "2px solid var(--color-accent)"
                      : "1px solid var(--color-border)",
                  cursor: "pointer",
                }}
              />
            ))}
          </div>
          <TextField
            id="cor"
            label="Nome da cor"
            placeholder="Ou digite o nome da cor"
            value={cor}
            onChange={(e) => setCor(e.target.value)}
            erro={erroCor}
          />
        </div>

        <SelectField
          id="estacao"
          label="Estação"
          opcoes={ESTACOES}
          value={estacao}
          onChange={(e) => setEstacao(e.target.value)}
          erro={erroEstacao}
        />

        <div style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
          <label htmlFor="foto" style={{ fontSize: "var(--font-size-sm)", fontWeight: 600 }}>
            Foto
          </label>
          <input id="foto" ref={inputFotoRef} type="file" accept="image/*" onChange={aoSelecionarFoto} />
          {enviandoFoto && <span style={{ fontSize: "var(--font-size-xs)" }}>Enviando foto...</span>}
          {fotoUrl && !enviandoFoto && (
            <img
              src={resolverUrlArquivo(fotoUrl)}
              alt="Pré-visualização da peça"
              style={{ width: "120px", height: "120px", objectFit: "cover", borderRadius: "8px" }}
            />
          )}
          {erroFoto && <span style={{ fontSize: "var(--font-size-xs)", color: "var(--color-danger)" }}>{erroFoto}</span>}
        </div>

        {erroGeral && <p style={{ color: "var(--color-danger)", margin: 0 }}>{erroGeral}</p>}

        <Button type="submit" disabled={enviando || enviandoFoto}>
          {enviando ? "Cadastrando..." : "Cadastrar peça"}
        </Button>
      </form>
    </div>
  );
}
