import React, { useContext } from "react";
import Toast from "react-bootstrap/Toast";
import ToastContainer from "react-bootstrap/ToastContainer";
import { ErrorContext } from "../../contexts/ErrorContext";

const NotificationError = () => {
    const { errorMessage, setErrorMessage } = useContext(ErrorContext);

    return (
        <ToastContainer
            position="top-end"
            className="p-3"
            style={{ zIndex: 9999 }}

        >
            {errorMessage && (
                <Toast
                    show={!!errorMessage}
                    onClose={() => setErrorMessage(null)}
                    bg="danger"
                    key={1}
                    delay={5000}
                    autohide
                >
                    <Toast.Header>
                        <strong className="me-auto">Error</strong>
                    </Toast.Header>
                    <Toast.Body className={"text-white"}>
                        {errorMessage}
                    </Toast.Body>
                </Toast>
            )}
        </ToastContainer>
    );
};

export default NotificationError;
