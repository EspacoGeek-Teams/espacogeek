import React from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

function TopBar() {
    return (
        <Navbar bg="dark" data-bs-theme="dark" className="bs-border-color-translucent">
            <Container>
                <Navbar.Brand href="#home" className="pr-28">
                    <img
                        alt=""
                        src=""
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                    />{" "}
                    Espaço Geek
                </Navbar.Brand>
                <Form inline>
                    <Row>
                        <Col xs="auto">
                            <Form.Control
                                type="text"
                                placeholder="Search"
                                className=" mr-sm-2"
                            />
                        </Col>
                    </Row>
                </Form>
                <Form inline name="login" className="pl-96">
                    <InputGroup>
                        <InputGroup.Text id="basic-addon1">👤</InputGroup.Text>
                        <Form.Control
                            placeholder="Email"
                            aria-label="Email"
                            id="email"
                            aria-describedby="basic-addon1"
                        />
                        <InputGroup.Text id="basic-addon1">🔑</InputGroup.Text>
                        <Form.Control
                            type="password"
                            placeholder="Password"
                            id="inputPassword5"
                            aria-describedby="passwordHelpBlock"
                        />
                    </InputGroup>
                </Form>
            </Container>
        </Navbar>
    );
}

export default TopBar;
