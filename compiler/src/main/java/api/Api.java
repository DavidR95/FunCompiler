package api;

import static spark.Spark.*;

public class Api {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            return "Hello world!";
        });
    }
}
