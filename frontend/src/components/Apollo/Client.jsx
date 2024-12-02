import { ApolloClient, InMemoryCache } from "@apollo/client";

const clientAPI = new ApolloClient({
    uri: window.location.href.split(":")[0].concat(":").concat(window.location.href.split(":")[1].concat(":8080")).concat("/api"),
    cache: new InMemoryCache(),
});

export default clientAPI;
