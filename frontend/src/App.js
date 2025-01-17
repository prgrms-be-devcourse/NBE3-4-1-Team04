import { useRoutes } from "react-router-dom";
import routes from "./routes/Router";

const App = () => {
  const routing = useRoutes(routes);
  return(
      <div>
        {routing}
      </div>
  );
}

export default App;
