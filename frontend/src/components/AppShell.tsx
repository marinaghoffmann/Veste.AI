import { Outlet } from "react-router-dom";

export function AppShell() {
  return (
    <div>
      <header
        style={{
          padding: "16px var(--font-size-lg)",
          borderBottom: "1px solid var(--color-border)",
        }}
      >
        <h1 style={{ fontSize: "var(--font-size-xl)", margin: 0 }}>Veste.AI</h1>
      </header>
      <main style={{ padding: "var(--font-size-lg)" }}>
        <Outlet />
      </main>
    </div>
  );
}
