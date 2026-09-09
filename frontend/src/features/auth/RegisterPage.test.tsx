import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { ApiError } from "../../lib/apiClient";
import * as authApi from "./authApi";
import { RegisterPage } from "./RegisterPage";

vi.mock("./authApi");

function renderRegisterPage() {
  return render(
    <MemoryRouter initialEntries={["/registro"]}>
      <Routes>
        <Route path="/registro" element={<RegisterPage />} />
        <Route path="/login" element={<p>Página de login</p>} />
      </Routes>
    </MemoryRouter>,
  );
}

describe("RegisterPage", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("mostra erro de validação para senha curta e confirmação divergente", async () => {
    renderRegisterPage();
    const usuario = userEvent.setup();

    await usuario.type(screen.getByLabelText("Nome"), "Ana Souza");
    await usuario.type(screen.getByLabelText("E-mail"), "ana@example.com");
    await usuario.type(screen.getByLabelText("Senha"), "123");
    await usuario.type(screen.getByLabelText("Confirmar senha"), "456");
    await usuario.click(screen.getByRole("button", { name: "Criar conta" }));

    expect(await screen.findByText("A senha deve ter ao menos 8 caracteres")).toBeInTheDocument();
    expect(screen.getByText("As senhas não coincidem")).toBeInTheDocument();
    expect(authApi.registrar).not.toHaveBeenCalled();
  });

  it("cadastra e navega para o login em caso de sucesso", async () => {
    vi.mocked(authApi.registrar).mockResolvedValue({ id: 1, nome: "Ana Souza", email: "ana@example.com" });
    renderRegisterPage();
    const usuario = userEvent.setup();

    await usuario.type(screen.getByLabelText("Nome"), "Ana Souza");
    await usuario.type(screen.getByLabelText("E-mail"), "ana@example.com");
    await usuario.type(screen.getByLabelText("Senha"), "senhaSegura123");
    await usuario.type(screen.getByLabelText("Confirmar senha"), "senhaSegura123");
    await usuario.click(screen.getByRole("button", { name: "Criar conta" }));

    await waitFor(() => expect(screen.getByText("Página de login")).toBeInTheDocument());
  });

  it("exibe mensagem de erro quando o e-mail já está cadastrado", async () => {
    vi.mocked(authApi.registrar).mockRejectedValue(
      new ApiError(409, "Já existe uma conta cadastrada com o e-mail ana@example.com"),
    );
    renderRegisterPage();
    const usuario = userEvent.setup();

    await usuario.type(screen.getByLabelText("Nome"), "Ana Souza");
    await usuario.type(screen.getByLabelText("E-mail"), "ana@example.com");
    await usuario.type(screen.getByLabelText("Senha"), "senhaSegura123");
    await usuario.type(screen.getByLabelText("Confirmar senha"), "senhaSegura123");
    await usuario.click(screen.getByRole("button", { name: "Criar conta" }));

    expect(
      await screen.findByText("Já existe uma conta cadastrada com o e-mail ana@example.com"),
    ).toBeInTheDocument();
  });
});
