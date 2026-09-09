import type { InputHTMLAttributes } from "react";

interface TextFieldProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
  erro?: string | null;
}

export function TextField({ label, erro, id, ...props }: TextFieldProps) {
  return (
    <div style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
      <label
        htmlFor={id}
        style={{ fontSize: "var(--font-size-sm)", fontWeight: 600, color: "var(--color-primary)" }}
      >
        {label}
      </label>
      <input
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
      />
      {erro && <span style={{ fontSize: "var(--font-size-xs)", color: "var(--color-danger)" }}>{erro}</span>}
    </div>
  );
}
