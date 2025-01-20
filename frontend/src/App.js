import { useRoutes } from "react-router-dom";
import { Suspense } from 'react';
import routes from "./routes/Router";

const App = () => {
    const routing = useRoutes(routes);
    return (
        <Suspense fallback={<div>Loading...</div>}>
            {routing}
        </Suspense>
    );
};

export default App;
