import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import Home from "./containers/home/Home";
import { ApolloProvider } from "@apollo/client";
import ClienteAPI from "./components/apollo/Client";
import NotificationError from "./components/toast/NotificationError";
import { ErrorProvider } from "./contexts/ErrorContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <ErrorProvider>
            <ApolloProvider client={ClienteAPI}>
                <main id="rootElement" data-bs-theme="dark">
                    <Home />
                    <NotificationError />
                </main>
            </ApolloProvider>
        </ErrorProvider>
    </React.StrictMode>
);
