import React from 'react';
import { createContext, useState } from "react";

const ErrorContext = createContext();

// eslint-disable-next-line react/prop-types
const ErrorProvider = ({ children }) => {
    const [errorMessage, setErrorMessage] = useState(null);

    return (
        <ErrorContext.Provider value={{ errorMessage, setErrorMessage }}>
            {children}
        </ErrorContext.Provider>
    );
};

export { ErrorProvider, ErrorContext };
