import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { ApolloProvider } from "@apollo/client";
import ClienteAPI from "./components/apollo/Client";
import NotificationError from "./components/toast/NotificationError";
import { ErrorProvider } from "./contexts/ErrorContext";
import { RouterProvider } from "react-router-dom";
import routes from "./routes/routes";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <ErrorProvider>
            <ApolloProvider client={ClienteAPI}>
                <main id="rootElement" data-bs-theme="dark">
                    <RouterProvider router={routes} />
                    <NotificationError />
                </main>
            </ApolloProvider>
        </ErrorProvider>
    </React.StrictMode>
);
