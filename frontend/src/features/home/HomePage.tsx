import { Link } from "react-router-dom";
import { Button } from "../../components/Button";
import { useAuth } from "../auth/useAuth";

export function HomePage() {
  const { usuarioEmail, sair } = useAuth();

  return (
    <div>
      <p style={{ color: "var(--color-text-muted)" }}>
        Guarda-roupa digital — front-end em construção.
      </p>
      <p>
        Logado como <strong>{usuarioEmail}</strong>
      </p>
      <div style={{ display: "flex", gap: "12px", alignItems: "center" }}>
        <Link to="/pecas/nova">Cadastrar peça</Link>
      </div>
      <div style={{ maxWidth: "200px", marginTop: "16px" }}>
        <Button variante="secundaria" onClick={sair}>
          Sair
        </Button>
      </div>
    </div>
  );
}
