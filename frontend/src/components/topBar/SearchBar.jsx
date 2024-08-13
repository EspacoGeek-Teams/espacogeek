import React from "react";
import CloseButton from 'react-bootstrap/CloseButton';

function SearchBar() {
    return (
        <>
            <div className="container absolute z-50">
                <CloseButton
                    // onClick={() => setSearchComponent(false)}
                />
            </div>
            <div className="absolute z-40 w-screen h-screen right-0 top-0 backdrop-blur-sm bg-blue-900 bg-opacity-10"></div>
        </>
    );
}

export default SearchBar;

