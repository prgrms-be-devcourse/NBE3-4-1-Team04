import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import AppRoutes from './routes/Router';

function App() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>
                {AppRoutes.map((route, index) => (
                    <Route
                        key={index}
                        path={route.path}
                        element={route.element}
                    />
                ))}
            </Routes>
        </Suspense>
    );
}

export default App;
