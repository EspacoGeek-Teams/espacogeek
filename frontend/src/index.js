import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { ApolloProvider } from "@apollo/client";
import ClientAPI from "./components/apollo/Client";
import { ErrorProvider } from "./contexts/ErrorContext";
import { RouterProvider } from "react-router-dom";
import routes from "./routes/routes";
import { PrimeReactProvider } from "primereact/api";
import { ErrorNotification, SuccessNotification } from "./components/toast/Notification";
import { SuccessProvider } from "./contexts/SuccessContext";
import { GlobalLoadingProvider } from "./contexts/GlobalLoadingContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
const primeReactConfig = {
    ripple: true,
};

root.render(
    <React.StrictMode>
        <ErrorProvider>
            <SuccessProvider>
                <GlobalLoadingProvider>
                    <ApolloProvider client={ClientAPI}>
                        <PrimeReactProvider value={primeReactConfig}>
                            <main id="rootElement" data-bs-theme="dark" className="h-screen w-screen">
                                <RouterProvider router={routes} />
                                <ErrorNotification />
                                <SuccessNotification />
                            </main>
                        </PrimeReactProvider>
                    </ApolloProvider>
                </GlobalLoadingProvider>
            </SuccessProvider>
        </ErrorProvider>
    </React.StrictMode>
);
