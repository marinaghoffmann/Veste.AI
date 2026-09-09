import type { SelectHTMLAttributes } from "react";

interface SelectFieldProps extends SelectHTMLAttributes<HTMLSelectElement> {
  label: string;
  erro?: string | null;
  opcoes: { valor: string; rotulo: string }[];
  placeholder?: string;
}

export function SelectField({ label, erro, id, opcoes, placeholder, ...props }: SelectFieldProps) {
  return (
    <div style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
      <label
        htmlFor={id}
        style={{ fontSize: "var(--font-size-sm)", fontWeight: 600, color: "var(--color-primary)" }}
      >
        {label}
      </label>
      <select
        {...props}
        id={id}
        style={{
          fontFamily: "var(--font-family-base)",
          fontSize: "var(--font-size-md)",
          padding: "10px 12px",
          borderRadius: "8px",
          border: `1px solid ${erro ? "var(--color-danger)" : "var(--color-border)"}`,
          background: "var(--color-surface)",
          color: "var(--color-primary)",
        }}
      >
        <option value="">{placeholder ?? "Selecione"}</option>
        {opcoes.map((opcao) => (
          <option key={opcao.valor} value={opcao.valor}>
            {opcao.rotulo}
          </option>
        ))}
      </select>
      {erro && <span style={{ fontSize: "var(--font-size-xs)", color: "var(--color-danger)" }}>{erro}</span>}
    </div>
  );
}
