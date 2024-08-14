import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Home from './containers/home/Home';
import { ApolloProvider } from '@apollo/client';
import ClienteAPI from './components/apollo/Client'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <main data-bs-theme="dark">
            <ApolloProvider client={ClienteAPI}>
                    <Home />
            </ApolloProvider>
        </main>
  </React.StrictMode>
);
