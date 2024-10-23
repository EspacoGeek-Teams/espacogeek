import React, { useContext, useRef, useEffect } from "react";
import { Toast } from 'primereact/toast';
import { ErrorContext } from "../../contexts/ErrorContext";

export function ErrorNotification() {
    const { errorMessage } = useContext(ErrorContext);
    const toastRef = useRef(null);

    useEffect(() => {
        if (errorMessage && toastRef.current) {
            toastRef.current.show({ severity: 'error', summary: 'Error', detail: errorMessage });
        }
    }, [errorMessage]);

    return (
        <>
            <Toast ref={toastRef} />
        </>
    );
}
