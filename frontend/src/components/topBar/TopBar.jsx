import React, { useState } from "react";
import Navbar from "react-bootstrap/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "react-bootstrap/Button";
import { Search } from "react-bootstrap-icons";
import SearchBar from "./SearchBar";
import SignInBar from "./SignInBar";
import LogInBar from "./LogInBar";
import { Toolbar } from "primereact/toolbar";
import { Avatar } from "primereact/avatar";
import Home from './../../containers/home/Home'

function TopBar() {
    const [SearchComponent, setSearchComponent] = useState(false);
    const [SignInComponent, setSignInComponent] = useState(false);
    const [LogInComponent, setLogInComponent] = useState(false);

    const handleSignInClose = () => setSignInComponent(false);
    const handleSignInShow = () => setSignInComponent(true);

    const handleLogInClose = () => setLogInComponent(false);
    const handleLogInShow = () => setLogInComponent(true);

    const startContent = <React.Fragment>EspaçoGeek</React.Fragment>;

    const centerContent = (
        <div className="flex flex-wrap align-items-center gap-3">
            <button className="p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200" >
                <i className="pi pi-home text-2xl"></i>
            </button>
            <button className="p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200">
                <i className="pi pi-user text-2xl"></i>
            </button>
            <button className="p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200">
                <i className="pi pi-search text-2xl"></i>
            </button>
        </div>
    );

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
                        <Button
                            variant="link"
                            className="group"
                            onClick={() => setSearchComponent(true)}
                        >
                            <Search
                                color="white"
                                className="group-hover:fill-blue-500 me-6 size-6"
                            />
                        </Button>
                        <Button
                            variant="primary"
                            className="me-1"
                            onClick={handleLogInShow}
                        >
                            Log in
                        </Button>
                        <Button
                            variant="outline-primary"
                            className="group"
                            onClick={handleSignInShow}
                        >
                            Sign in
                        </Button>
                    </div>
                </div>
            </Navbar>

            {SearchComponent && <SearchBar />}
            {SignInComponent && (
                <SignInBar
                    show={SignInComponent}
                    handleClose={handleSignInClose}
                />
            )}
            {LogInComponent && (
                <LogInBar
                    show={LogInComponent}
                    handleClose={handleLogInClose}
                />
            )}
        </>
    );
}

export default TopBar;
