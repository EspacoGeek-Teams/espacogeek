import React, { createContext, useState } from "react";

export const GlobalLoadingContext = createContext();

// eslint-disable-next-line no-unused-vars
export const GlobalLoadingProvider = ({ children }) => {
    const [globalLoading, setGlobalLoading] = useState(false);

    return (
        <GlobalLoadingContext.Provider value={{ globalLoading, setGlobalLoading }}>
            {children}
        </GlobalLoadingContext.Provider>
    );
};
