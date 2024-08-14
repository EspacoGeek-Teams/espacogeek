import { ApolloClient, InMemoryCache } from '@apollo/client';

const clientAPI = new ApolloClient({
    uri: "http://localhost:8080/api",
    cache: new InMemoryCache(),
});

export default clientAPI;
