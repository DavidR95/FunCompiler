package api;

import static spark.Spark.*;
import java.io.*;
import com.google.gson.Gson;

public class Api {

    public static void main(String[] args) {

        // Create a GSON object, used to convert objects to JSON
        Gson gson = new Gson();

        // Post request at route '/', convert output to JSON
        post("/", (req, res) -> {
            // Set the content-type of the response to JSON
            res.type("application/json");
            // Get the input program sent from the web app
            String program = req.queryParams("program");
            // Get the execution type sent from the web app
            String type = req.queryParams("type");
            // Convert the input String to an InputStream
            InputStream programInputStream = new ByteArrayInputStream(program.getBytes());
            // Pass the InputStream to the Fun compiler
            return FunRun.execute(programInputStream, type);
        }, gson::toJson);

    }
}
