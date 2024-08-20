import Form from 'react-bootstrap/Form';
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { Link } from 'react-router-dom';

function LogIn({show, handleClose}){

    return (
        <>
            <Modal show={show} onHide={handleClose}>

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

            </Modal>
        </>
    );
}

export default LogIn;
