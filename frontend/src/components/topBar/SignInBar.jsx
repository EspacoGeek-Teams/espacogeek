/* eslint-disable no-unused-vars */
import React, { useContext, useState } from "react";
import { useMutation } from "@apollo/client";
import singInMutation from "../apollo/schemas/mutations/signInUser";
import { ErrorContext } from "../../contexts/ErrorContext";
import { Dialog } from "primereact/dialog";
import { Form, Formik } from "formik";
import { InputText } from "primereact/inputtext";
import { FloatLabel } from "primereact/floatlabel";
import { Button } from "primereact/button";

// eslint-disable-next-line react/prop-types
function SignIn({ show, handleClose }) {
    const [newUser, { data, loading, error }] = useMutation(singInMutation);
    const { setErrorMessage } = useContext(ErrorContext);

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    function handleSubmit(values) {
        if (values.password === values.confirmPassword) {
            newUser({
                variables: {
                    username: values.username,
                    email: values.email,
                    password: values.password,
                },
            })
                .then((data) => {
                    console.log(data.data.createUser);
                    //mensagem ou 'icon'/imagem de OK, maybe um certinho
                })
                .catch((error) => {
                    setErrorMessage(error.graphQLErrors[0].message);
                    //mensagem ou 'icon'/imagem de FAIL, maybe um erradinho
                });
        } else {
            setErrorMessage("Incorret Password");
        }

        if (!loading) handleClose();
    };

    return (
        <>
            <Dialog
                visible={show}
                modal
                onHide={() => handleClose()}
                content={({ hide }) => (
                    <div className="flex flex-col text-center items-center justify-center p-4 gap-4 bg-gradient-to-tr from-sky-700 rounded-xl">
                        <h1 className="select-none p-3 font-bold">EG</h1>
                        <Formik
                            initialValues={{
                                email: "",
                                password: "",
                                confirmPassword: "",
                                username: "",
                            }}
                            onSubmit={(values, { setSubmitting }) => {
                                setTimeout(() => {
                                    handleSubmit(values);
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
                                <Form
                                    onSubmit={handleSubmit}
                                    className="flex gap-7 flex-col"
                                >
                                    <FloatLabel>
                                        <InputText
                                            type="email"
                                            name="email"
                                            required={false}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="bg-white-alpha-20 border-none p-3 text-primary-50"
                                            value={values.email}
                                        />
                                        {errors.email &&
                                            touched.email &&
                                            errors.email}
                                        <label htmlFor="email">Email</label>
                                    </FloatLabel>
                                    <FloatLabel>
                                        <InputText
                                            type="username"
                                            name="username"
                                            required={false}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="bg-white-alpha-20 border-none p-3 text-primary-50"
                                            value={values.username}
                                        />
                                        {errors.username &&
                                            touched.username &&
                                            errors.username}
                                        <label htmlFor="username">Username</label>
                                    </FloatLabel>
                                    <FloatLabel>
                                        <InputText
                                            type="password"
                                            name="password"
                                            required={false}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="bg-white-alpha-20 border-none p-3 text-primary-50"
                                            value={values.password}
                                        />
                                        {errors.password &&
                                            touched.password &&
                                            errors.password}
                                        <label htmlFor="password">Password</label>
                                    </FloatLabel>
                                    <FloatLabel>
                                        <InputText
                                            type="password"
                                            name="confirmPassword"
                                            required={false}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="bg-white-alpha-20 border-none p-3 text-primary-50"
                                            value={values.confirmPassword}
                                        />
                                        {errors.confirmPassword &&
                                            touched.confirmPassword &&
                                            errors.confirmPassword}
                                        <label htmlFor="username">Confirm Password</label>
                                    </FloatLabel>
                                    <div className="flex align-items-center gap-2">
                                        <Button
                                            label="Cancel"
                                            disabled={isSubmitting}
                                            onClick={(e) => hide(e)}
                                            text
                                            className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10"
                                        />
                                        <Button
                                            label="Sing In"
                                            type="submit"
                                            disabled={isSubmitting}
                                            onClick={(e) => hide(e)}
                                            text
                                            className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10"
                                        />
                                    </div>
                                </Form>
                            )}
                        </Formik>
                    </div>
                )}
            />

            {/* <Modal show={show} onHide={handleClose} centered>

                <Modal.Header closeButton>
                    <Modal.Title>
                        <div className="text-center">Join EspaçoGeek</div>
                    </Modal.Title>
                </Modal.Header>
                <Form onSubmit={handleSubmit}>
                    <Modal.Body>
                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlInput1"
                        >
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                type="name"
                                placeholder="name example"
                            />
                        </Form.Group>

                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlInput1"
                        >
                            <Form.Label>Email Address</Form.Label>
                            <Form.Control
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                type="email"
                                placeholder="name@example.com"
                            />
                        </Form.Group>

                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlTextarea1"
                        >
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                type="password"
                                placeholder="123password"
                            />
                        </Form.Group>

                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlTextarea2"
                        >
                            <Form.Label>Confirm Password</Form.Label>
                            <Form.Control
                                value={confirmPassword}
                                onChange={(e) =>
                                    setConfirmPassword(e.target.value)
                                }
                                type="password"
                                placeholder="123password"
                            />
                        </Form.Group>
                    </Modal.Body>

                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Close
                        </Button>
                        <Button
                            variant="primary"
                            type="submit"
                            onClick={handleSubmit}
                        >
                            Sign Up
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal> */}
        </>
    );
}

export default SignIn;
