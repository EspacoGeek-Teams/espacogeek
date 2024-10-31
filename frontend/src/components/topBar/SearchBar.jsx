import React, { useEffect, useState, useContext } from "react";
import { useQuery } from '@apollo/client';
import searchQuery from '../apollo/schemas/queries/tvserieSearch';
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { InputIcon } from 'primereact/inputicon';
import { IconField } from 'primereact/iconfield';
import { ErrorContext } from "../../contexts/ErrorContext";

function SearchBar({ handleClose }) {
    const [value, setValue] = useState('');
    const { loading, error, data, refetch } = useQuery(searchQuery, { variables: { id: /^\d+$/.test(value) ? parseInt(value) : null, name: value } });
    const { setErrorMessage } = useContext(ErrorContext);

    useEffect(() => {
        console.log(data);
        if (error) {
            setErrorMessage(error.graphQLErrors?.[0]?.message);
        }
    }, [data]);

    return (
        <>
            <div className="fixed top-1/4 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 flex items-center">
                <IconField iconPosition="left">
                    <InputText
                        placeholder="Search"
                        value={value}
                        autoFocus
                        className="px-9 p-4 text-xl"
                        onInput={(e) => { setValue(e.target.value); refetch(); }} />
                    <InputIcon className={loading ? "pi pi-spin pi-spinner" : "pi pi-search"} />
                </IconField>
                <Button
                    icon="pi pi-times"
                    className="p-button-text"
                    onClick={handleClose}
                    aria-label="Close"
                />
            </div>
            {/* {
        "tvserie": [
            {
            "__typename": "Media",
            "id": "16616",
            "name": "Stranger Things",
            "cover": "https://image.tmdb.org/t/p/original/49WJfeN0moxb9IPfGn8AIqMGskD.jpg"
            },
            {
            "__typename": "Media",
            "id": "19799",
            "name": "Beyond Stranger Things",
            "cover": "https://image.tmdb.org/t/p/original/rHCFO8RJ3Hg6a8KjWAsvAsa38hp.jpg"
            }
        ]
        } */}
            <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 flex items-center">
                {data?.tvserie?.map((tvserie) => (
                    <div key={tvserie.id} className="fixed top-1/5 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 flex items-center">{tvserie.name}</div>
                ))}
            </div>
            <div className="fixed top-0 left-0 right-0 bottom-0 z-40 backdrop-blur-sm bg-blue-900 bg-opacity-10"></div>
        </>
    );
}

export default SearchBar;
