import { ApolloClient, InMemoryCache } from "@apollo/client";

const clientAPI = new ApolloClient({
    uri: window.location.href.substring(0, window.location.href.lastIndexOf(":")).concat(":8080/api"),
    cache: new InMemoryCache(),
});

export default clientAPI;
