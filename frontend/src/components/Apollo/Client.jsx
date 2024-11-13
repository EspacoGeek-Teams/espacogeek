import { ApolloClient, InMemoryCache } from "@apollo/client";

const clientAPI = new ApolloClient({
    uri: window.location.href.substring(0, window.location.href.lastIndexOf(":")).concat("/api"),
    cache: new InMemoryCache(),
});

export default clientAPI;
