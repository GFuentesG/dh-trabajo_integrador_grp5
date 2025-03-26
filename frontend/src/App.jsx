import { Routes, Route, useLocation } from "react-router-dom";

import { Home, AdminPage } from "./pages/index";
import {
  Header,
  HeaderMobile,
  ProtectedRoute,
  Restricted,
  Profile,
} from "./components/index";
import "./App.css";
import { ProductDetail } from "./pages/detailProduct/detailProduct";
import { Gallery } from "./pages/gallery/Gallery";
import { AuthProvider } from "./context/AuthContext";
import { Footer } from "./components/footer/Footer";
import { SearchPage } from "./pages/search/SearchPage";
import Favorites from "./pages/favorite/favorites";

// 1) IMPORTAR BookingReview
import BookingReview from "./pages/bookingReview/BookingReview";

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
        <Route path="/profile" element={<Profile />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/favorites" element={<Favorites />} />

        {/* 2) NUEVA RUTA HACIA BookingReview */}
        <Route path="/booking-review" element={<BookingReview />} />

        {/*<Route path="*" element={<NotFoundPage />} />*/}
      </Routes>

      {!isRestrictedPage && !isAdminPage && <Footer />}
    </AuthProvider>
  );
}

export default App;
