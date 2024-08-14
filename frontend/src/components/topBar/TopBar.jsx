import React, { useState } from "react";
import Navbar from "react-bootstrap/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "react-bootstrap/Button";
import { Search } from "react-bootstrap-icons";
import SearchBar from "./SearchBar";
import SignInBar from "./SignInBar";

function TopBar() {
    const [SearchComponent, setSearchComponent] = useState(false);
    const [SignInComponent, setSignInComponent] = useState(false);

    const handleSignInClose = () => setSignInComponent(false);
    const handleSignInShow = () => setSignInComponent(true);

    return (
        <>
            <Navbar
                bg="dark"
                data-bs-theme="dark"
                className="bg-slate-600 z-30"
            >
                <div className="container">
                    <Navbar.Brand href="/" className="pr-28">
                        <img
                            alt=""
                            src=""
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                        />{" "}
                        Espaço Geek
                    </Navbar.Brand>
                    <div className="d-flex align-items-center">
                        <Button variant="link" className="group" onClick={() => setSearchComponent(true)}>
                            <Search
                                color="white"
                                className="group-hover:fill-blue-500 me-6 size-6"
                            />
                        </Button>
                        <Button variant="primary" className="me-1">
                            Log in
                        </Button>
                        <Button variant="outline-primary" className="group" onClick={handleSignInShow}>
                            Sign in
                        </Button>
                    </div>
                </div>
            </Navbar>
            {SearchComponent&&<SearchBar/>}
            {SignInComponent&&<SignInBar show={SignInComponent} handleClose={handleSignInClose} />}
        </>
    );
}

export default TopBar;
