import React from "react";
import TopBar from "../components/topBar/TopBar";
import './star.css';

function Home() {
    return (
        <>
            <TopBar />
            <div>
                <div id="stars1"></div>
                <div id="stars2"></div>
                <div id="stars3"></div>
            </div>
        </>
    );
}

export default Home;
