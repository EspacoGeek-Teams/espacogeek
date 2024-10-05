import React from "react";
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";
import { Form, Formik } from "formik";

function LogIn({ show, handleClose }) {
    const headerElement = (
        <div className="inline-flex align-items-center justify-content-center gap-2">
            <span className="font-bold white-space-nowrap">Login</span>
        </div>
    );

    const footerContent = (
        <div>
            <Button
                label="Ok"
                icon="pi pi-check"
                onClick={() => handleClose()}
                autoFocus
            />
        </div>
    );

    return (
        <>
            <Dialog
                visible={show}
                modal
                header={headerElement}
                footer={footerContent}
                style={{ width: "50rem" }}
                onHide={() => handleClose()}
                resizable={false}
                draggable={false}
                position="top"
                breakpoints={{ "960px": "50vw", "641px": "75vw" }}
            >
                <Formik
                    initialValues={{ email: "", password: "" }}
                    validate={(values) => {
                        const errors = {};
                        if (!values.email) {
                            errors.email = "Required";
                        } else if (
                            !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(
                                values.email
                            )
                        ) {
                            errors.email = "Invalid email address";
                        }

                        if (!values.password) {
                            errors.password = "Required";
                        } else if (values.password.length() < 8) {
                            errors.password = "Invalid password";
                        }
                        return errors;
                    }}
                    onSubmit={(values, { setSubmitting }) => {
                        setTimeout(() => {
                            alert(JSON.stringify(values, null, 2));
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {({
                        values,
                        errors,
                        touched,
                        handleChange,
                        handleBlur,
                        handleSubmit,
                        isSubmitting,
                    }) => (
                        <Form onSubmit={handleSubmit}>
                            
                        </Form>
                    )}
                </Formik>
            </Dialog>
            {/* <Modal show={show} onHide={handleClose}>

                <Modal.Header closeButton>
                    <Modal.Title>
                        <div className="text-center">In EspaçoGeek</div>
                    </Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>Email Address</Form.Label>
                                <Form.Control
                                    type="email"
                                />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder='password'
                                />
                        </Form.Group>
                        <div className="flex justify-end pb-2">
                            <Link to="recoverPassword">Forgot your Password?</Link>
                        </div>
                    </Form>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleClose}>
                        Sign In
                    </Button>
                </Modal.Footer>

            </Modal> */}
        </>
    );
}

export default LogIn;
