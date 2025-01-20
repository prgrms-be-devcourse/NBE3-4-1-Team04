import { lazy } from "react";
import { Navigate } from "react-router-dom";

const Home = lazy(() => import("../pages/home/Home"));

const AppRoutes = [
    { path: "/", element: <Home /> },
    // { path: "/payment", element: <Payment /> },
    { path: "*", element: <Navigate to="/" /> }  // 유효하지 않는 URL 입력 시 root URL로 이동
];

export default AppRoutes;
