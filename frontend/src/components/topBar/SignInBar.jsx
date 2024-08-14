import React from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

function SigIn({show, handleClose}) {
    return (
        <>
            <Modal show={show} onHide={handleClose} centered>

                <Modal.Header closeButton>
                    <Modal.Title>
                        <div className="text-center">Join EspaçoGeek</div>
                    </Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>User Name</Form.Label>
                                <Form.Control
                                    type="name"
                                    placeholder="name example"
                                />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>Email Address</Form.Label>
                                <Form.Control
                                    type="email"
                                    placeholder="name@example.com"
                                />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
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

