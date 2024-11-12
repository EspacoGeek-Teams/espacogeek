import React from "react";
import { TopBar } from "../../components/layout/Layout";
import "./star.css";
import { useEffect } from "react";

function Home() {
    useEffect(() => {
        document.title = "Home - EspaçoGeek";
    }, []);

    return (
        <>
            <TopBar />
            <div className="-z-40 min-h-screen">
                <div id="stars1"></div>
                <div id="stars2"></div>
                <div id="stars3"></div>
            </div>
        </>
    );
}

export default Home;
