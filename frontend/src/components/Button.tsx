import type { ButtonHTMLAttributes, CSSProperties } from "react";

type Variante = "primaria" | "secundaria";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variante?: Variante;
}

const estiloBase: CSSProperties = {
  fontFamily: "var(--font-family-base)",
  fontSize: "var(--font-size-md)",
  fontWeight: 600,
  borderRadius: "8px",
  padding: "12px 20px",
  cursor: "pointer",
  border: "1px solid transparent",
  width: "100%",
};

const estilosPorVariante: Record<Variante, CSSProperties> = {
  primaria: {
    background: "var(--color-primary)",
    color: "var(--color-surface)",
  },
  secundaria: {
    background: "transparent",
    color: "var(--color-primary)",
    borderColor: "var(--color-border)",
  },
};

export function Button({ variante = "primaria", style, disabled, ...props }: ButtonProps) {
  return (
    <button
      {...props}
      disabled={disabled}
      style={{
        ...estiloBase,
        ...estilosPorVariante[variante],
        opacity: disabled ? 0.6 : 1,
        cursor: disabled ? "not-allowed" : "pointer",
        ...style,
      }}
    />
  );
}
