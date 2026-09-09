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
      <div style={{ maxWidth: "200px" }}>
        <Button variante="secundaria" onClick={sair}>
          Sair
        </Button>
      </div>
    </div>
  );
}
