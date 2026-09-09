import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { ApiError } from "../../lib/apiClient";
import * as authApi from "./authApi";
import { AuthProvider } from "./AuthContext";
import { LoginPage } from "./LoginPage";

vi.mock("./authApi");

function renderLoginPage() {
  return render(
    <AuthProvider>
      <MemoryRouter initialEntries={["/login"]}>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/" element={<p>Página inicial</p>} />
        </Routes>
      </MemoryRouter>
    </AuthProvider>,
  );
}

describe("LoginPage", () => {
  beforeEach(() => {
    localStorage.clear();
    vi.clearAllMocks();
  });

  it("mostra erros de validação quando os campos estão vazios", async () => {
    renderLoginPage();
    const usuario = userEvent.setup();

    await usuario.click(screen.getByRole("button", { name: "Entrar" }));

    expect(await screen.findByText("Informe seu e-mail")).toBeInTheDocument();
    expect(screen.getByText("Informe sua senha")).toBeInTheDocument();
    expect(authApi.login).not.toHaveBeenCalled();
  });

  it("navega para a página inicial após login bem-sucedido", async () => {
    vi.mocked(authApi.login).mockResolvedValue({ token: "token-fake", tipo: "Bearer" });
    renderLoginPage();
    const usuario = userEvent.setup();

    await usuario.type(screen.getByLabelText("E-mail"), "maria@example.com");
    await usuario.type(screen.getByLabelText("Senha"), "senhaSegura123");
    await usuario.click(screen.getByRole("button", { name: "Entrar" }));

    await waitFor(() => expect(screen.getByText("Página inicial")).toBeInTheDocument());
    expect(localStorage.getItem("vesteai.token")).toBe("token-fake");
  });

  it("exibe mensagem de erro quando as credenciais são inválidas", async () => {
    vi.mocked(authApi.login).mockRejectedValue(new ApiError(401, "E-mail ou senha inválidos"));
    renderLoginPage();
    const usuario = userEvent.setup();

    await usuario.type(screen.getByLabelText("E-mail"), "maria@example.com");
    await usuario.type(screen.getByLabelText("Senha"), "senhaErrada");
    await usuario.click(screen.getByRole("button", { name: "Entrar" }));

    expect(await screen.findByText("E-mail ou senha inválidos")).toBeInTheDocument();
  });
});
