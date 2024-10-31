import React, { useEffect, useState } from "react";
import { useQuery } from '@apollo/client';
import searchQuery from '../apollo/schemas/queries/tvserieSearch';
import { InputText } from "primereact/inputtext";

function SearchBar() {
    // const { loading, error, data } = useQuery(searchQuery, { variables: { name: "Stranger Things" } });
    const [value, setValue] = useState('');
    const { loading, error, data, refetch } = useQuery(searchQuery, { variables: { name: value } });

    useEffect(() => {
        console.log(data);
    }, [data]);

    // if (loading) return 'Submitting...';
    // if (error) return `Submission error! ${error.message}`;

    return (
        <>
            <div className="container absolute z-50">
                <InputText
                    type="search"
                    placeholder="Search"
                    value={value}
                    onInput={(e) => { setValue(e.target.value); refetch(); }} />
            </div>
            <div className="absolute z-40 w-screen h-screen right-0 top-0 backdrop-blur-sm bg-blue-900 bg-opacity-10"></div>
        </>
    );
}

export default SearchBar;
