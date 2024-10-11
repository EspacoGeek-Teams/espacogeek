import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { ApolloProvider } from "@apollo/client";
import ClienteAPI from "./components/apollo/Client";
import { ErrorProvider } from "./contexts/ErrorContext";
import { RouterProvider } from "react-router-dom";
import routes from "./routes/routes";
import { PrimeReactProvider } from "primereact/api";

const root = ReactDOM.createRoot(document.getElementById("root"));
const primeReactConfig = {
    ripple: true,
};

root.render(
    <React.StrictMode>
        <ErrorProvider>
            <ApolloProvider client={ClienteAPI}>
                <PrimeReactProvider value={primeReactConfig}>
                    <main id="rootElement" data-bs-theme="dark">
                        <RouterProvider router={routes} />
                    </main>
                </PrimeReactProvider>
            </ApolloProvider>
        </ErrorProvider>
    </React.StrictMode>
);
