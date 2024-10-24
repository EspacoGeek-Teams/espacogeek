import React from 'react';
import { createContext, useState } from "react";

const SuccessContext = createContext();

// eslint-disable-next-line react/prop-types
const SuccessProvider = ({ children }) => {
    const [successMessage, setSuccessMessage] = useState(null);

    return (
        <SuccessContext.Provider value={{ successMessage, setSuccessMessage }}>
            {children}
        </SuccessContext.Provider>
    );
};

export { SuccessProvider, SuccessContext };
