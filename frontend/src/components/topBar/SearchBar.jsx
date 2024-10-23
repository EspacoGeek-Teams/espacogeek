import React, { useEffect, useState } from "react";
import { useQuery } from '@apollo/client';
import searchQuery from '../apollo/schemas/queries/tvserieSearch';
import { InputText } from "primereact/inputtext";

function SearchBar() {
    const { loading, error, data } = useQuery(searchQuery, { variables: { name: "Stranger Things" } });
    const [value, setValue] = useState('');

    async function handleSubmit(values) {

    }

    // // Test of GraphQL, doc: https://www.apollographql.com/docs/react/get-started#step-5-fetch-data-with-usequery
    // useEffect(() => {
    //     console.log(data);
    // }, [data]);

    if (loading) return 'Submitting...';
    if (error) return `Submission error! ${error.message}`;

    return (
        <>
            <div className="container absolute z-50">
                {/* Do a InputText from primereact to search using useQuery from Apollo when type */}
                <InputText
                    type="search"
                    placeholder="Search"
                    value={value} onChange={(e) => setValue(e.target.value)} />
            </div>
            <div className="absolute z-40 w-screen h-screen right-0 top-0 backdrop-blur-sm bg-blue-900 bg-opacity-10"></div>
        </>
    );
}


export default SearchBar;
