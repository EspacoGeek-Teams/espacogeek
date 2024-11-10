import React, { useContext, useEffect } from "react";
import { TopBar, Footer } from "../../components/layout/Layout";
import { useParams } from "react-router-dom";
import { useQuery } from "@apollo/client";
import mediaQuery from "../../components/apollo/schemas/queries/mediaQuery";
import './media.css';
import { Card } from 'primereact/card';
import { Skeleton } from 'primereact/skeleton';
import { Image } from 'primereact/image';
import { GlobalLoadingContext } from "../../contexts/GlobalLoadingContext";
import { ScrollPanel } from 'primereact/scrollpanel';
import YouTube from "../../components/youTubeEmbed/YouTubeEmbed";
import { Timeline } from 'primereact/timeline';

export default function Media() {
    const { mediaId } = useParams();
    const { loading, data } = useQuery(mediaQuery, { variables: { id: mediaId } });
    const { setGlobalLoading } = useContext(GlobalLoadingContext);

    useEffect(() => {
        setGlobalLoading(loading);
    }, [loading]);

    async function setTitle() {
        document.title = `${data?.media?.name ?? 'Media'} - EspaçoGeek`;
    }

    useEffect(() => {
        setTitle();
    }, [data?.media.name]);

    function convertDateFormat(dateString) {
        const date = dateString.split(/\s+/g)[0];
        const parts = date.replace(/-/g, '/').replace('00:00:00.0', '').replace(/\s+/g, '').split('/');
        if (parts.length !== 3) {
            throw new Error("Invalid date format. Expected format: YYYY/MM/DD");
        }
        const [year, month, day] = parts;
        return `${day}/${month}/${year}`;
    }

    const seasonMarker = (item) => {
        return (
            <span className="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle z-1 shadow-1">
                <i className="pi pi-circle"></i>
            </span>
        );
    };

    const sessonContent = (item) => {
        return (
            <>
                <p>{convertDateFormat(item?.airDate)}</p>
                {item.cover && <img src={item?.cover} width={200} className="shadow-1" />}
            </>
        );
    };

    return (
        <>
            <TopBar />
            <div className="relative -top-28 z-0">
                <div className="w-full h-96 bg-cover bg-no-repeat bg-[50%_35%] absolute top-0" style={{ backgroundImage: `url(${data?.media.banner})` }} hidden={loading}>
                    <div className="w-full h-full backdropmask"></div>
                </div>
                <Skeleton width="100%" height="24rem" className="!absolute top-0" hidden={!loading}>
                    <div className="w-full h-full backdropmask"></div>
                </Skeleton>
            </div>
            <div className="relative top-24 pl-0 flex gap-5 flex-col items-center md:pl-28 md:flex-row md:justify-start">
                <div className="w-56">
                    <Skeleton width="100%" height="14rem" className="rounded-lg shadow-2xl" hidden={!loading} />
                    <Image src={data?.media.cover} alt={data?.media.name} width="250" preview />
                </div>
                <div className="flex gap-1 flex-col items-center md:items-start">
                    <div className="md:pt-10 w-25 md:w-[30rem]">
                        <Skeleton width="100%" height="2rem" hidden={!loading} />
                        <h2 className="text-3xl font-bold" hidden={loading}>{data?.media.name}</h2>
                    </div>
                    <div className="pt-5 md:w-[30rem] p-5 md:p-0">
                        <Skeleton className="mb-2" hidden={!loading} />
                        <Skeleton width="10rem" className="mb-2" hidden={!loading} />
                        <Skeleton className="mb-2" hidden={!loading} />
                        <Skeleton width="5rem" className="mb-2" hidden={!loading} />
                        <Skeleton className="mb-2" hidden={!loading} />
                        <Skeleton className="mb-2" hidden={!loading} />
                        <ScrollPanel className="w-full md:h-40">
                            <p hidden={loading}>{data?.media.about}</p>
                        </ScrollPanel>
                    </div>
                </div>
            </div>
            <div className="relative flex flex-col md:flex-row items-center md:pl-28 pt-28 md:items-start gap-5">
                <Card className="w-80 md:w-56 bg-slate-700 bg-opacity-15 border-none shadow-lg">
                    <div className="flex w-full h-full flex-col gap-4 p-0 m-0">
                        <div>
                            <Skeleton width="70%" height="1rem" hidden={!loading} />
                            <Skeleton width="5rem" height="0.5rem" className="mb-2 mt-2" hidden={!loading} />
                            <p className="text-1x1 font-bold" hidden={loading}>Category</p>
                            <p className="pt-2" hidden={loading}>{data?.media?.mediaCategory?.typeCategory}</p>
                        </div>
                        <div className={data?.media?.genre === null ? "hidden" : ""}>
                            <Skeleton width="70%" height="1rem" hidden={!loading} />
                            <Skeleton width="5rem" height="0.5rem" className="mb-2 mt-2" hidden={!loading} />
                            <p className="text-1x1 font-bold">Genres</p>
                            {data?.media?.genre.slice(0, -1).map(genre => genre?.name).join(", ")}
                            {data?.media?.genre.length > 1 ? ` and ${data?.media?.genre[data?.media?.genre.length - 1].name}` : data?.media?.genre[0]?.name}
                        </div>
                        <div className={data?.media?.totalEpisodes === null ? "hidden" : ""}>
                            <Skeleton width="70%" height="1rem" hidden={!loading} />
                            <Skeleton width="5rem" height="0.5rem" className="mb-2 mt-2" hidden={!loading} />
                            <p className="text-1x1 font-bold">Total Episodes</p>
                            <p className="pt-2">{data?.media?.totalEpisodes}</p>
                        </div>
                        <div className={data?.media?.episodeLength === null ? "hidden" : ""}>
                            <Skeleton width="70%" height="1rem" hidden={!loading} />
                            <Skeleton width="5rem" height="0.5rem" className="mb-2 mt-2" hidden={!loading} />
                            <p className="text-1x1 font-bold">Episode Length</p>
                            <p className="pt-2">{data?.media?.episodeLength} minutes</p>
                        </div>
                        <div className={data?.media?.alternativeTitles === null ? "hidden" : ""}>
                            <Skeleton width="70%" height="1rem" hidden={!loading} />
                            <Skeleton width="5rem" height="0.5rem" className="mb-2 mt-2" hidden={!loading} />
                            <p className="text-1x1 font-bold">Others Tittles</p>
                            {data?.media?.alternativeTitles.slice(0, -1).map(alternativeTitles => alternativeTitles?.name).join(", ")}
                            {data?.media?.alternativeTitles.length > 1 ? ` and ${data?.media?.alternativeTitles[data?.media?.alternativeTitles.length - 1].name}` : data?.media?.alternativeTitles[0]?.name}
                        </div>
                    </div>
                </Card>
                {data?.media?.externalReference?.filter(reference => reference.typeReference.nameReference === 'YouTube')[0]?.reference &&
                    <div className="h-full w-1/2">
                        <Skeleton width="100%" height="20rem" className="rounded-lg m-0 p-0" hidden={!loading} />
                        <div hidden={loading}>
                            <YouTube videoId={data?.media?.externalReference?.filter(reference => reference.typeReference.nameReference === 'YouTube')[0]?.reference} />
                        </div>
                    </div>
                }
                {console.log(data?.media?.season)}
                {data?.media?.season &&
                    <div className="h-full w-72">
                        <Timeline value={data?.media?.season} align="alternate" className="customized-timeline" marker={seasonMarker} content={sessonContent} />
                    </div>}
            </div>
            <Footer />
        </>
    );
}
