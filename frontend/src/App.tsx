import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AppShell } from "./components/AppShell";
import { AuthProvider } from "./features/auth/AuthContext";
import { LoginPage } from "./features/auth/LoginPage";
import { RegisterPage } from "./features/auth/RegisterPage";
import { RequireAuth } from "./features/auth/RequireAuth";
import { HomePage } from "./features/home/HomePage";
import { CadastrarPecaPage } from "./features/pecas/CadastrarPecaPage";
import { CadastrarPecaPageIA } from "./features/pecas/CadastrarPecaPageIA";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<AppShell />}>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/registro" element={<RegisterPage />} />
            <Route
              path="/"
              element={
                <RequireAuth>
                  <HomePage />
                </RequireAuth>
              }
            />
            <Route
              path="/pecas/nova"
              element={
                <RequireAuth>
                  <CadastrarPecaPage />
                </RequireAuth>
              }
            />
          </Route>
          <Route
            path="/pecas/nova/ia"
            element={
              <RequireAuth>
                <CadastrarPecaPageIA />
              </RequireAuth>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
