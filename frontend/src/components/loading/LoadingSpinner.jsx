import React from "react";
import Spinner from "react-bootstrap/Spinner";

function SpinnerAnimation() {
    return (
        <Spinner animation="border">
            <span className="visually-hidden">Loading...</span>
        </Spinner>
    )
}

function LoadContainer() {
    return (
        <div className="absolute self-center w-full h-full bg-gray-700 flex z-50 items-center justify-center bg-opacity-50">
            <SpinnerAnimation />
        </div>
    )
}

/**
 *
 * @param show Boolean
 * @returns loading animation page full
 */
// eslint-disable-next-line react/prop-types
function Loading({ show }) {

    return (
        show && <LoadContainer />
    )
}

export default Loading;
