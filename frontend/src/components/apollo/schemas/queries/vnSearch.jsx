import { gql } from '@apollo/client';

const query = gql`
    query MediaPage($id: ID, $name: String) {
        vn(id: $id, name: $name){
            content {
                id
                name
                cover
            }
        }
    }
`

export default query;
