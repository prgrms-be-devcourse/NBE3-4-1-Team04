import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import routes from './routes/Router.jsx';
import './App.css';

const App = () => {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>
                {routes.map((route) => (
                    <Route
                        key={route.path}
                        path={route.path}
                        element={route.element}
                    />
                ))}
            </Routes>
        </Suspense>
    );
};

export default App;
