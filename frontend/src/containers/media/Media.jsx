import React, { useEffect } from "react";
import TopBar from "../../components/topBar/TopBar";
import { useParams } from "react-router-dom";
import { useQuery } from "@apollo/client";
import mediaQuery from "../../components/apollo/schemas/queries/mediaQuery";
import './media.css';
import { Card } from 'primereact/card';

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
            <div className="relative top-24 pl-0 flex gap-5 flex-col items-center md:pl-28 md:flex-row md:justify-start">
                <div className="w-56">
                    <img src={data?.media.cover} alt={data?.media.name} className="rounded-lg shadow-2xl" />
                </div>
                <div className="flex gap-1 flex-col items-center md:items-start">
                    <div className="pt-10">
                        <h2 className="text-3xl font-bold">{data?.media.name}</h2>
                    </div>
                    <div className="pt-5 md:w-[30rem] p-5 md:p-0">
                        <p>{data?.media.about}</p>
                    </div>
                </div>
            </div>
            <div className="relative pl-28 pt-32">
                <Card className="w-56 bg-slate-700 bg-opacity-15 border-none shadow-lg [&>.p-card-body]p-0">
                    <div className="flex w-full h-full flex-col gap-4 p-0 m-0">
                        <div>
                            <p className="text-1x1 font-bold">Category</p>
                            <p className="pt-2">{data?.media.mediaCategory.typeCategory}</p>
                        </div>
                        <div>
                            <p className="text-1x1 font-bold">Genres</p>
                            {data?.media.genre.slice(0, -1).map(genre => genre.name).join(", ")}
                            {data?.media.genre.length > 1 ? ` and ${data?.media.genre[data?.media.genre.length - 1].name}` : data?.media.genre[0].name}
                        </div>
                        <div>
                            <p className="text-1x1 font-bold">Total Episodes</p>
                            <p className="pt-2">{data?.media.totalEpisodes}</p>
                        </div>
                        <div>
                            <p className="text-1x1 font-bold">Episode Length</p>
                            <p className="pt-2">{data?.media.episodeLength}</p>
                        </div>
                        <div>
                            <p className="text-1x1 font-bold">Others Tittles</p>
                            {data?.media.alternativeTitles.slice(0, -1).map(alternativeTitles => alternativeTitles.name).join(", ")}
                            {data?.media.alternativeTitles.length > 1 ? ` and ${data?.media.alternativeTitles[data?.media.alternativeTitles.length - 1].name}` : data?.media.alternativeTitles[0].name}
                        </div>
                    </div>
                </Card>
            </div>
        </>
    );
}
