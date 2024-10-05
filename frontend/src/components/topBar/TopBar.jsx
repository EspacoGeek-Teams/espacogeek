import React, { useState } from "react";
import SearchBar from "./SearchBar";
import SignInBar from "./SignInBar";
import LogInBar from "./LogInBar";
import { Toolbar } from "primereact/toolbar";
import { Link } from "react-router-dom";
import { Button } from "primereact/button";
import { Ripple } from 'primereact/ripple';

function TopBar() {
    const [SearchComponent, setSearchComponent] = useState(false);
    const [SignInComponent, setSignInComponent] = useState(false);
    const [LogInComponent, setLogInComponent] = useState(false);

    const handleSignInClose = () => setSignInComponent(false);
    const handleSignInShow = () => setSignInComponent(true);

    const handleLogInClose = () => setLogInComponent(false);
    const handleLogInShow = () => setLogInComponent(true);

    const startContent = (
        <div className="flex flex-wrap align-items-center">
            <h5>EG</h5>
        </div>
    );

    const centerContent = (
        <div className="flex flex-wrap align-items-center gap-3">
            <Link
                to={`/`}
                className="p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200 no-underline p-ripple"
            >
                <i className="pi pi-home text-2xl" />
                <Ripple />
            </Link>
            <button
                className="p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200 p-ripple"
                type="button"
                onClick={() => setSearchComponent(true)}
            >
                <i className="pi pi-search text-2xl" />
                <Ripple />
            </button>
        </div>
    );

    const endContent = (
        <React.Fragment>
            {/* <div className="flex align-items-center gap-2">
                <Avatar
                    image="https://primefaces.org/cdn/primereact/images/avatar/amyelsner.png"
                    shape="circle"
                />
                <span className="font-bold text-bluegray-50">Amy Elsner</span>
            </div> */}
            <div className="flex align-items-center gap-2">
                <Button
                    type="button"
                    label="Login"
                    icon="pi pi-user"
                    rounded
                    outlined
                    onClick={handleLogInShow}
                />
                <Button
                    type="button"
                    label="Sign In"
                    icon="pi pi-sign-in"
                    rounded
                    outlined
                    onClick={handleSignInShow}
                />
            </div>
        </React.Fragment>
    );

    return (
        <>
            <div className="card pt-2 pl-2 pr-2">
                <Toolbar
                    start={startContent}
                    center={centerContent}
                    end={endContent}
                    className="z-50 sticky"
                    style={{
                        borderRadius: "3rem",
                        backgroundImage:
                            "linear-gradient(to right, var(--bluegray-700), var(--bluegray-900))",
                    }}
                />
            </div>
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
