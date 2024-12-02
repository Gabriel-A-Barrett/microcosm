package application;

import sockets.Server;
import sockets.pojos.HttpResponse;
import sockets.http.TemperatureHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static sockets.contract.HttpMethod.GET;

/**
 * Test functional server library.
 */
public class App {
    public static void main(String[] args) throws IOException {
        Server myServer = new Server(8080);

        // Initialize the TemperatureHandler
        TemperatureHandler temperatureHandler = new TemperatureHandler();

        myServer.addRoute(GET, "/microcosm",
                (req) -> new HttpResponse.Builder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "text/html")
                        .setEntity(loadStaticFile("index.html")) // renders html page
                        .build());

        // Serve static JS files
        myServer.addRoute(GET, "/js/functions.js",
                (req) -> new HttpResponse.Builder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "application/javascript")
                        .setEntity(loadStaticFile("js/functions.js"))
                        .build());

        // Serve css file 
        myServer.addRoute(GET, "css/style.css",
                (req) -> new HttpResponse.Builder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "text/css")
                        .setEntity(loadStaticFile("css/style.css"))
                        .build());

        // Route for serving temperature data as JSON
        myServer.addRoute(GET, "/api/temperature-data",
                (req) -> new HttpResponse.Builder()
                    .setStatusCode(200)
                    .addHeader("Access-Control-Allow-Origin", "*")
                    .setEntity(temperatureHandler.getTemperatureData()) // sends temperature json data
                    .build());

        myServer.start();
    }

    private static String loadStaticFile(String fileName) {
        try {
        // Get the absolute path to the webapp folder
            String basePath = Paths.get("src/main/webapp").toAbsolutePath().toString();
            return Files.readString(Paths.get(basePath, fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return "<HTML><P>Error loading the requested file.</P></HTML>";
        }
    }
}