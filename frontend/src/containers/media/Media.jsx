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
                <image src={data?.media.banner} alt={data?.media.name} />
            </div>
        </>
    );
}
