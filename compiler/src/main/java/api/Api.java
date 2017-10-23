package api;

import static spark.Spark.*;
import java.io.*;
import com.google.gson.Gson;
import fun.FunRun;

public class Api {

    public static void main(String[] args) {

        // Create a GSON object, used to convert objects to JSON
        Gson gson = new Gson();

        get("/", (req, res) -> {
            return "Hello world!";
        });

        // Post request at route '/', convert output to JSON
        post("/", (req, res) -> {
            // Get the input program sent from the web app
            String program = req.queryParams("program");
            // Convert the input String to an InputStream
            InputStream programInputStream = new ByteArrayInputStream(program.getBytes());
            // Pass the InputStream to the Fun compiler
            return FunRun.execute(programInputStream);
        }, gson::toJson);

    }
}
