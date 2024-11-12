import { gql } from "@apollo/client";

const query = gql`
    query Media($id: ID) {
        media(id: $id) {
            id
            name
            season {
                id
                name
                about
                airDate
                endAirDate
                cover
                seasonNumber
                episodeCount
            }
            totalEpisodes
            episodeLength
            about
            cover
            banner
            mediaCategory {
                id
                typeCategory
            }
            genre {
                id
                name
            }
            externalReference {
                id
                reference
                typeReference {
                    id
                    nameReference
                }
            }
            alternativeTitles {
                id
                name
            }
        }
    }
`;

export default query;
