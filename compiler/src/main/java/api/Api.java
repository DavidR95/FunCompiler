package api;

import static spark.Spark.*;
import java.io.*;
import com.google.gson.Gson;
import fun.FunRun;

public class Api {

    public static void main(String[] args) {
        Gson gson = new Gson();
        get("/", (req, res) -> {
            return "Hello world!";
        });
        post("/", (req, res) -> {
            String program = "proc main (: write7).";
            InputStream programInputStream = new ByteArrayInputStream(program.getBytes());
            return FunRun.execute(programInputStream);
        }, gson::toJson);
    }
}
