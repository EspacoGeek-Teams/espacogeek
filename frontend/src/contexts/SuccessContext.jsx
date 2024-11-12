import React, { createContext, useState } from 'react';

export const SuccessContext = createContext();

// eslint-disable-next-line react/prop-types
export const SuccessProvider = ({ children }) => {
    const [successMessage, setSuccessMessage] = useState("");

    return (
        <SuccessContext.Provider value={{ successMessage, setSuccessMessage }}>
            {children}
        </SuccessContext.Provider>
    );
};
