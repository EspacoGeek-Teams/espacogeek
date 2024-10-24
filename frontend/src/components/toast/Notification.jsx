import React, { useContext, useRef, useEffect } from "react";
import { Toast } from 'primereact/toast';
import { ErrorContext } from "../../contexts/ErrorContext";
import { SuccessContext } from "../../contexts/SuccessContext";

export function ErrorNotification() {
    const { errorMessage } = useContext(ErrorContext);
    const toastRef = useRef(null);

    useEffect(() => {
        if (errorMessage && toastRef.current) {
            toastRef.current.show({ severity: 'error', detail: errorMessage, life: 3000  });
        }
    }, [errorMessage]);

    return (
        <>
            <Toast ref={toastRef} />
        </>
    );
}

export function SuccessNotification() {
    const { successMessage } = useContext(SuccessContext);
    const toastRef = useRef(null);

    useEffect(() => {
        if (successMessage && toastRef.current) {
            toastRef.current.show({ severity: 'success', detail: successMessage, life: 3000  });
        }
    }, [successMessage]);

    return (
        <>
            <Toast ref={toastRef} />
        </>
    );
}
