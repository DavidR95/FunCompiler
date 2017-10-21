package api;

import static spark.Spark.*;
import java.io.*;
import fun.FunRun;

public class Api {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            return "Hello world!";
        });
        post("/", (req, res) -> {
            String program = "proc main (): wrizste(7).";
            InputStream programInputStream = new ByteArrayInputStream(program.getBytes());
            return FunRun.execute(programInputStream).getNumSyntaxErrors();
        });
    }
}
