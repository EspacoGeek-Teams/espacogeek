import { gql } from '@apollo/client';

const query = gql`
    query QuoteArtwork {
        quote{
            quote
            author
            urlArtwork
        }
    }
`

export default query;
