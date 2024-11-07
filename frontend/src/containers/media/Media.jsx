import React from "react";
import TopBar from "../../components/topBar/TopBar";
import { useParams } from "react-router-dom";
import { useQuery } from "@apollo/client";
import mediaQuery from "../../components/apollo/schemas/queries/mediaQuery";

export default function Media() {
    const { mediaId } = useParams();
    const { loading, error, data } = useQuery(mediaQuery, { variables: { id: mediaId } });

    return (
        <>
            <TopBar />
            <div>
                <div className={`w-full h-96 bg-cover bg-no-repeat bg-[50%_35%]`} style={{ backgroundImage: `url(${data?.media.banner})` }}></div>
                {/* <img src={data?.media.banner} alt={data?.media.name} className={`h-96 bg-cover bg-no-repeat bg-[50%_35%]`} /> */}
            </div>
        </>
    );
}
