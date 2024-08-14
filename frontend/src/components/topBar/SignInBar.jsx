import React from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import { useMutation } from '@apollo/client';
import singInMutation from '../Apollo/schemas/mutations/signInUser';


function SigIn({ show, handleClose }) {
    const [newUser, { data, loading, error }] = useMutation(singInMutation);
    let username;
    let email;
    let password;

    if (loading) return 'Submitting...';
    if (error) return `Submission error! ${error.message}`;

    return (
        <>
            <Modal show={show} onHide={handleClose} centered>

                <Modal.Header closeButton>
                    <Modal.Title>
                        <div className="text-center">Join EspaçoGeek</div>
                    </Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form onSubmit={e => {
                        e.preventDefault();
                        newUser({ variables: { username: username, email: email, password: password } });
                        username.value = null;
                        email.value = null;
                        password.value = null;
                    }}>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>User Name</Form.Label>
                            <Form.Control ref={node => {
                                username = node;
                                }}
                                    type="name"
                                    placeholder="name example"
                                />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>Email Address</Form.Label>
                                <Form.Control ref={node => {
                                email = node;
                                }}
                                    type="email"
                                    placeholder="name@example.com"
                                />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Password</Form.Label>
                            <Form.Control ref={node => {
                                password = node;
                                }}
                                type="password"
                                placeholder="123password"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea2">
                            <Form.Label>Confirm Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="123password"
                            />
                        </Form.Group>

                    </Form>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleClose}>
                        Sign Up
                    </Button>
                </Modal.Footer>

            </Modal>
        </>
    );
}

export default SigIn;

