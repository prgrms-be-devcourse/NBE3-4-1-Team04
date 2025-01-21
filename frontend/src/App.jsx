import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import routes from './routes/Router.jsx';
import './App.css';


const App = () => {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>

                {/* 기존 routes 배열에 정의된 경로를 렌더링 */}
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
