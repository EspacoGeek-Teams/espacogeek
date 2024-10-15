import React, { useState } from "react";
import SearchBar from "./SearchBar";
import SignInBar from "./SignInBar";
import LogInBar from "./LogInBar";
import { Toolbar } from "primereact/toolbar";
import { useNavigate } from "react-router-dom";
import { Button } from "primereact/button";
import { Ripple } from "primereact/ripple";
import { SpeedDial } from "primereact/speeddial";

function TopBar() {
    const navigate = useNavigate();

    const [SearchComponent, setSearchComponent] = useState(false);
    const [SignInComponent, setSignInComponent] = useState(false);
    const [LogInComponent, setLogInComponent] = useState(false);

    const handleSignInClose = () => setSignInComponent(false);
    const handleSignInShow = () => setSignInComponent(true);

    const handleLogInClose = () => setLogInComponent(false);
    const handleLogInShow = () => setLogInComponent(true);

    const handleNavToHome = () => navigate("/");

    const startContent = (
        <div className="flex flex-wrap align-items-center">
            <h5 className="select-none">EG</h5>
        </div>
    );

    const centerContent = (
        <div className="flex flex-wrap align-items-center gap-3">
            <Button
                onClick={handleNavToHome}
                className="text-1xl p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200 p-ripple"
                label="Home"
                type="button"
                icon="pi pi-home"
            >
                <Ripple />
            </Button>
            <Button
                className="text-1xl p-link inline-flex justify-content-center align-items-center text-white h-3rem w-3rem border-circle hover:bg-white-alpha-10 transition-all transition-duration-200 p-ripple"
                label="Search"
                icon="pi pi-search"
                type="button"
                onClick={() => setSearchComponent(true)}
            >
                <Ripple />
            </Button>
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

    const items = [
        {
            label: "Home",
            icon: "pi pi-home",
            command: () => {
                window.location.href = "https://react.dev/";
            },
        },
    ];

    return (
        <>
            <div className="card pt-2 pl-2 pr-2 hidden md:block">
                <Toolbar
                    start={startContent}
                    center={centerContent}
                    end={endContent}
                    className="z-40 bg-slate-500 bg-opacity-10 backdrop-blur-sm rounded-full sticky"
                />
            </div>

            <div className="card block md:hidden">
                <SpeedDial
                    mask
                    showIcon="pi pi-bars"
                    hideIcon="pi pi-times"
                    buttonClassName="p-button-outlined"
                    transitionDelay={80}
                    model={items}
                    radius={120}
                    direction="up"
                    style={{ right: 0, bottom: 0 }}
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
