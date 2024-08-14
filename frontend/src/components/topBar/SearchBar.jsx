import React, { useEffect } from "react";
import CloseButton from 'react-bootstrap/CloseButton';
import { useQuery } from '@apollo/client';
import searchQuery from '../apollo/schemas/queries/tvserieSearch';

function SearchBar() {
    const { loading, error, data } = useQuery(searchQuery, { variables: { name: "Stranger Things" } });

    // Test of GraphQL, doc: https://www.apollographql.com/docs/react/get-started#step-5-fetch-data-with-usequery
    useEffect(() => {
        console.log(data);
    }, [data]);

    if (loading) return 'Submitting...';
    if (error) return `Submission error! ${error.message}`;

    return (
        <>
            <div className="container absolute z-50">
                <CloseButton
                    // onClick={() => setSearchComponent(false)}
                />
                <CloseButton />
            </div>
            <div className="absolute z-40 w-screen h-screen right-0 top-0 backdrop-blur-sm bg-blue-900 bg-opacity-10"></div>
        </>
    );
}


export default SearchBar;
