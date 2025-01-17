import {lazy} from "react";
import { Navigate } from "react-router-dom";

const Home = lazy(() => import("../pages/home/Home"));

/*****Routes******/
const AppRoutes = [
    {
        path: "/",
        element: <Home/>,
        children: [
            { path: "*", element: <Navigate to="/"/> },
        ]
    }
];

export default AppRoutes;