import React from "react";
import PropTypes from "prop-types";

const YoutubeEmbed = ({ videoId }) => (
    <div className="overflow-hidden pb-[56.25%] relative h-full w-full rounded-md">
        <iframe
            className="absolute top-0 left-0 w-full h-full"
            width="853"
            height="480"
            src={`https://www.youtube.com/embed/${videoId}`}
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowFullScreen
            title="Embedded youtube"
        />
    </div>
);

YoutubeEmbed.propTypes = {
    videoId: PropTypes.string.isRequired
};

export default YoutubeEmbed;
