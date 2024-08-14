import { gql } from '@apollo/client';

const mutation = gql`
    mutation createUser($username: String, $email: String, $password: String) {
        createUser(credentials: {
                username: $username,
                email: $email,
                password: $password
            })
    }
`

export default mutation;
