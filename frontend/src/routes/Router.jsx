import React from 'react';
import { lazy } from 'react';
import { Navigate } from "react-router-dom";


const Home = lazy(() => import('../pages/home/Home.jsx'));
const Payment = lazy(() => import('../pages/payment/Payment.jsx'));
const Orders = lazy(() => import('../pages/orders/Orders.jsx'));
const OrderList = lazy(() => import('../pages/orderlist/OrderList.jsx'));
const Administer = lazy(() => import('../pages/administer/Administer.jsx'));
const Dashboard = lazy(() => import('../pages/administer/Dashboard.jsx'));

const routes = [
    {
        path: '/',
        element: <Home />
    },
    {
        path: '/payment',
        element: <Payment />
    },
    {
        path: '/orders',
        element: <Orders />
    },
    {
        path: '/orderlist',
        element: <OrderList />
    },
    {
        path: '/administer',
        element: <Administer />
    },
    {
        path: '/dashboard',
        element: <Dashboard />,
    },
    { path: "*", element: <Navigate to="/" /> }
];

export default routes; 