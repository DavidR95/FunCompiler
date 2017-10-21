package api;

import static spark.Spark.*;
import fun.FunRun;

public class Api {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            return "Hello world!";
        });
        post("/", (req, res) -> {
            return FunRun.execute("proc main (): write(7).").toString();
        });
    }
}
