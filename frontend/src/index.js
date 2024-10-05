import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { ApolloProvider } from "@apollo/client";
import ClienteAPI from "./components/apollo/Client";
import NotificationError from "./components/toast/NotificationError";
import { ErrorProvider } from "./contexts/ErrorContext";
import { RouterProvider } from "react-router-dom";
import routes from "./routes/routes";
import { PrimeReactProvider } from "primereact/api";
import "primereact/resources/themes/lara-dark-blue/theme.css";
import 'primeicons/primeicons.css';

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
                        <NotificationError />
                    </main>
                </PrimeReactProvider>
            </ApolloProvider>
        </ErrorProvider>
    </React.StrictMode>
);
