import React, { useEffect } from "react";
import TopBar from "../../components/topBar/TopBar";
import { useParams } from "react-router-dom";
import { useQuery } from "@apollo/client";
import mediaQuery from "../../components/apollo/schemas/queries/mediaQuery";
import './media.css';

export default function Media() {
    const { mediaId } = useParams();
    const { loading, error, data } = useQuery(mediaQuery, { variables: { id: mediaId } });

    async function setTitle() {
        document.title = `${await data?.media.name} - EspaçoGeek`;
    }

    useEffect(() => {
        setTitle();
    }, []);

    return (
        <>
            <TopBar />
            <div className="relative -top-28 z-0">
                <div className={`w-full h-96 bg-cover bg-no-repeat bg-[50%_35%] absolute top-0`} style={{ backgroundImage: `url(${data?.media.banner})` }}>
                    <div className="w-full h-full backdropmask"></div>
                </div>
            </div>
            <div className="relative top-24 pl-28 flex gap-5 flex-row">
                <div className="w-56">
                    <img src={data?.media.cover} alt={data?.media.name} className="rounded-lg shadow-2xl" />
                </div>
                <div className="flex gap-1 flex-col">
                    <div className="pt-10">
                        <h2 className="text-3xl font-bold">{data?.media.name}</h2>
                    </div>
                    <div className="pt-5 w-96">
                        <p>{data?.media.about}</p>
                    </div>
                </div>
            </div>
        </>
    );
}
