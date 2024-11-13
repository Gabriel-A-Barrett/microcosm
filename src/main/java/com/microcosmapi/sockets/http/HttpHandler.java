package sockets.http;

import sockets.contract.RequestRunner;
import sockets.pojos.HttpRequest;
import sockets.pojos.HttpResponse;
import sockets.writers.ResponseWriter;

import java.io.*;
import java.util.Map;
import java.util.Optional;

public class HttpHandler {

    private final Map<String, RequestRunner> routes;

    public HttpHandler(final Map<String, RequestRunner> routes) {
        this.routes = routes;
    }
    public void handleConnection(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        /*
         * Decode http request
         */
        Optional<HttpRequest> request = HttpDecoder.decode(inputStream);

        /*
         * point to the correct RequestRunner
         * write Response to output Stream 
         * 400: invalid request
         * 404: route not found
         */
        request.ifPresentOrElse((r) -> handleRequest(r, bufferedWriter), () -> handleInvalidRequest(bufferedWriter));

        bufferedWriter.close();
        inputStream.close();
    }

    private void handleInvalidRequest(final BufferedWriter bufferedWriter) {
        HttpResponse notFoundResponse = new HttpResponse.Builder().setStatusCode(400).setEntity("Invalid Request...").build();
        ResponseWriter.writeResponse(bufferedWriter, notFoundResponse);
    }

    private void handleRequest(final HttpRequest request, final BufferedWriter bufferedWriter) {
        final String routeKey = request.getHttpMethod().name().concat(request.getUri().getRawPath());
        System.out.println("Handling route key: " + routeKey);  // Debugging line

        if (routes.containsKey(routeKey)) {
            ResponseWriter.writeResponse(bufferedWriter, routes.get(routeKey).run(request));
        } else {
            // Not found
            ResponseWriter.writeResponse(bufferedWriter, new HttpResponse.Builder().setStatusCode(404).setEntity("Route Not Found...").build());
        }
    } 

}
