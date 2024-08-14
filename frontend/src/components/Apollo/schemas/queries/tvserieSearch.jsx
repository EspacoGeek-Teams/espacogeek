import { gql } from '@apollo/client';

const query = gql`
    query Media($id: ID, $name: String) {
        tvserie(id: $id, name: $name){
            id
            name
            cover
        }
    }
`

export default query;
