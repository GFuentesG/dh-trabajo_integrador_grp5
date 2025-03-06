import { Routes, Route, useLocation } from "react-router-dom";

import { Home, AdminPage } from "./pages/index";
import {
  Header,
  HeaderMobile,
  ProtectedRoute,
  Restricted,
} from "./components/index";
import "./App.css";
import { ProductDetail } from "./pages/detailProduct/detailProduct";
import { Gallery } from "./pages/gallery/Gallery";
import { AuthProvider } from "./context/AuthContext";
import { Footer } from "./components/footer/Footer";

function App() {
  const location = useLocation();
  const isRestrictedPage = location.pathname.startsWith("/restricted");
  const isAdminPage = location.pathname.startsWith("/admin");
  return (
    <AuthProvider>
      {!isRestrictedPage && !isAdminPage && <Header />}
      {!isRestrictedPage && !isAdminPage && <HeaderMobile />}

      <Routes>
        <Route
          path="/admin/*"
          element={
            <ProtectedRoute>
              <AdminPage />
            </ProtectedRoute>
          }
        />
        <Route path="/" element={<Home />} />
        <Route path="/product/:id" element={<ProductDetail />} />
        <Route path="/gallery" element={<Gallery />} />
        <Route path="/restricted" element={<Restricted />} />
        {/*<Route path="*" element={<NotFoundPage />} />*/}
      </Routes>
      {!isRestrictedPage && <Footer />}
    </AuthProvider>
  );
}

export default App;
