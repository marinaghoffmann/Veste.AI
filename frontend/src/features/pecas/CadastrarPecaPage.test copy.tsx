import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { ApiError } from "../../lib/apiClient";
import { CadastrarPecaPage } from "./CadastrarPecaPage";
import * as pecasApi from "./pecasApi";

vi.mock("./pecasApi");

function renderPage() {
  return render(
    <MemoryRouter>
      <CadastrarPecaPage />
    </MemoryRouter>,
  );
}

describe("CadastrarPecaPage", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("mostra erros de validação quando os campos estão vazios", async () => {
    renderPage();
    const usuario = userEvent.setup();

    await usuario.click(screen.getByRole("button", { name: "Cadastrar peça" }));

    expect(await screen.findByText("Informe o nome da peça")).toBeInTheDocument();
    expect(screen.getByText("Selecione uma categoria")).toBeInTheDocument();
    expect(screen.getByText("Selecione ou informe uma cor")).toBeInTheDocument();
    expect(screen.getByText("Selecione uma estação")).toBeInTheDocument();
    expect(screen.getByText("Envie uma foto da peça")).toBeInTheDocument();
    expect(pecasApi.cadastrarPeca).not.toHaveBeenCalled();
  });

  it("envia a foto, preenche o formulário e cadastra a peça com sucesso", async () => {
    vi.mocked(pecasApi.enviarFoto).mockResolvedValue({ fotoUrl: "/uploads/pecas/foto-teste.png" });
    vi.mocked(pecasApi.cadastrarPeca).mockResolvedValue({
      id: 1,
      nome: "Camisa social",
      categoria: "PARTE_DE_CIMA",
      cor: "branco",
      estacao: "TODAS",
      fotoUrl: "/uploads/pecas/foto-teste.png",
      disponivel: true,
    });

    renderPage();
    const usuario = userEvent.setup();

    await usuario.type(screen.getByLabelText("Nome"), "Camisa social");
    await usuario.selectOptions(screen.getByLabelText("Categoria"), "PARTE_DE_CIMA");
    await usuario.click(screen.getByTitle("branco"));
    await usuario.selectOptions(screen.getByLabelText("Estação"), "TODAS");

    const arquivo = new File(["conteudo"], "camisa.png", { type: "image/png" });
    await usuario.upload(screen.getByLabelText("Foto"), arquivo);

    await waitFor(() => expect(pecasApi.enviarFoto).toHaveBeenCalledWith(arquivo));

    await usuario.click(screen.getByRole("button", { name: "Cadastrar peça" }));

    await waitFor(() =>
      expect(pecasApi.cadastrarPeca).toHaveBeenCalledWith({
        nome: "Camisa social",
        categoria: "PARTE_DE_CIMA",
        cor: "branco",
        estacao: "TODAS",
        fotoUrl: "/uploads/pecas/foto-teste.png",
      }),
    );
    expect(await screen.findByText("Peça cadastrada!")).toBeInTheDocument();
  });

  it("exibe mensagem de erro quando o envio da foto falha", async () => {
    vi.mocked(pecasApi.enviarFoto).mockRejectedValue(new ApiError(500, "Não foi possível enviar a foto"));

    renderPage();
    const usuario = userEvent.setup();

    const arquivo = new File(["conteudo"], "camisa.png", { type: "image/png" });
    await usuario.upload(screen.getByLabelText("Foto"), arquivo);

    expect(await screen.findByText("Não foi possível enviar a foto")).toBeInTheDocument();
  });
});
