/* eslint-disable no-unused-vars */
import React, { useContext, useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Modal from "react-bootstrap/Modal";
import { useMutation } from "@apollo/client";
import singInMutation from "../apollo/schemas/mutations/signInUser";
import Loading from "../loading/LoadingSpinner";
import { ErrorContext } from "../../contexts/ErrorContext";

// eslint-disable-next-line react/prop-types
function SignIn({ show, handleClose }) {
    const [newUser, { data, loading, error }] = useMutation(singInMutation);
    const { setErrorMessage } = useContext(ErrorContext);

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    let handleSubmit = (e) => {
        e.preventDefault();

        if (password === confirmPassword) {
            newUser({
                variables: {
                    username: username,
                    email: email,
                    password: password,
                },
            }).then((data) => {
                console.log(data.data.createUser);
            }).catch((error) => {
                setErrorMessage(error.graphQLErrors[0].message);
            })
        }

        if (!loading) handleClose();
    };

    return (
        <>
            <Modal show={show} onHide={handleClose} centered>
                <Loading show={loading} />

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
            </Modal>
        </>
    );
}

export default SignIn;
