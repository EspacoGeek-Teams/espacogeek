import React, { useEffect, useState, useContext } from "react";
import { useQuery } from '@apollo/client';
import searchTvSerieQuery from '../apollo/schemas/queries/tvserieSearch';
import searchGameQuery from '../apollo/schemas/queries/gameSearch';
import searchVNQuery from '../apollo/schemas/queries/vnSearch';
import searchMovieQuery from '../apollo/schemas/queries/movieSearch';
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { InputIcon } from 'primereact/inputicon';
import { IconField } from 'primereact/iconfield';
import { ErrorContext } from "../../contexts/ErrorContext";
import { DataView } from 'primereact/dataview';
import { ListBox } from 'primereact/listbox';
import { useNavigate } from "react-router-dom";
import { Dropdown } from 'primereact/dropdown';
import { GlobalLoadingContext } from "../../contexts/GlobalLoadingContext";

// eslint-disable-next-line react/prop-types
function SearchBar({ handleClose }) {
    const [value, setValue] = useState('');
    const { setErrorMessage } = useContext(ErrorContext);
    const [selectedQuery, setSelectedQuery] = useState({ name: 'TVSerie', code: 'tvserie' });
    const navigate = useNavigate();
    const { setGlobalLoading } = useContext(GlobalLoadingContext);
    const { loading, error, data } = useQuery(
        selectedQuery?.code === 'tvserie' ? searchTvSerieQuery :
        selectedQuery?.code === 'game' ? searchGameQuery :
        selectedQuery?.code === 'vn' ? searchVNQuery : null,
        { variables: { id: /^\d+$/.test(value) ? parseInt(value) : null, name: value } }
    );

    useEffect(() => {
        setGlobalLoading(loading);
    }, [loading]);

    const queries = [
        { name: 'TVSerie', code: 'tvserie' },
        { name: 'Game', code: 'game' },
        { name: 'Visual Novel', code: 'vn' },
    ];

    const handleMediaClick = (id, name) => {
        if (window.location.pathname.startsWith('/media')) {
            window.location.href = `/media/${id}/${name}`;
        } else {
            handleClose();
            navigate(`media/${id}/${name}`);
        }
    };

    useEffect(() => {
        if (error) {
            setErrorMessage(error.graphQLErrors?.[0]?.message);
        }
    }, [error]);

    const itemTemplate = (media) => {
        return (
            <div className="select-none hover:bg-slate-300/10 rounded-lg cursor-pointer" key={media.id} onClick={() => handleMediaClick(media.id, encodeURIComponent(media.name.replace(/\s+/g, '-').toLowerCase()))}>
                <div className="flex flex-row align-items-start p-4 gap-4">
                    <img className="w-9 sm:w-12 shadow-sm block mx-auto rounded-lg" src={media.cover} alt={media.name} />
                    <div className="flex flex-row justify-content-between align-items-start flex-1 gap-4">
                        <div className="flex flex-col align-items-start gap-3">
                            <div className="text-2xl font-bold">{media.name}</div>
                        </div>
                    </div>
                </div>
            </div>
        );
    };

    return (
        <>
            <div className="fixed flex flex-col items-center w-full h-full z-50 mt-10">
                <div className="flex items-start gap-5 flex-col md:flex-row z-50">
                    <Dropdown value={selectedQuery} onChange={(e) => setSelectedQuery(e.value)} options={queries} optionLabel="name"  className="w-full md:w-14rem md:hidden" />
                    <ListBox value={selectedQuery} onChange={(e) => setSelectedQuery(e.value)} options={queries} optionLabel="name" className="w-36 hidden md:block" style={{ backgroundColor: 'transparent' }} />
                    <div className="flex flex-col items-center gap-2">
                        <div className="flex items-center">
                            <IconField iconPosition="left">
                                <InputText
                                    placeholder="Search"
                                    value={value}
                                    autoFocus
                                    className="px-12 p-4 text-xl"
                                    onInput={(e) => { setValue(e.target.value); }} />
                                <InputIcon className={loading ? "pi pi-spin pi-spinner" : "pi pi-search"} />
                            </IconField>
                            <Button
                                icon="pi pi-times"
                                className="p-button-text"
                                onClick={handleClose}
                                aria-label="Close" />
                        </div>
                        <div className="max-h-96 overflow-y-auto">
                            {data && (
                                <DataView
                                    value={data?.[selectedQuery?.code].content}
                                    itemTemplate={itemTemplate}
                                    className="flex flex-col gap-6 [&>_.p-dataview-content]:bg-transparent max-w-[24rem]"
                                    emptyMessage=" " />
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <div className="fixed top-0 left-0 right-0 bottom-0 z-40 backdrop-blur-sm bg-blue-900 bg-opacity-10"></div>
        </>
    );
}

export default SearchBar;


