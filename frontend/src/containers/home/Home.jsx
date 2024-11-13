import React from "react";
import { Footer, TopBar } from "../../components/layout/Layout";
import "./star.css";
import { useEffect } from "react";

function Home() {
    useEffect(() => {
        document.title = "Home - EspaçoGeek";
    }, []);

    return (
        <>
            <TopBar />
            <div className="-z-40 bg-animation absolute">
                <div id="stars"></div>
                <div id="stars2"></div>
                <div id="stars3"></div>
                <div id="stars4"></div>
            </div>
            <div className="w-screen h-screen flex flex-col justify-center items-center">
                <div className="w-1/2 pb-96">
                    <h1 className="animate-bounce text-4xl font-bold text-center">Welcome to EspaçoGeek!</h1>
                    <p className="text-center">Your ultimate hobby tracker for movies, series, anime, and games!</p>
                </div>
            </div>
            <div>
                <Footer />
            </div>
        </>
    );
}

export default Home;
