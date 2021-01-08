package program;

import exception.AdminNotFoundException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.exception;
import static spark.Spark.staticFiles;

public class Program {
    private static final Logger logger = Logger.getAnonymousLogger();
    private static final ProgramFactory factory = ProgramFactory.buildFactory();

    public static void main(String[] args) throws IOException {
        port(8080);
        staticFiles.externalLocation(new File("./static").getCanonicalPath());

        path("/api", () -> {
            before("/*", (request, response) -> logger.log(Level.INFO,
                    request.requestMethod() + " " + request.uri() + " " + request.protocol()));

            after("/*", (request, response) -> {
                response.type(ProgramFactory.APPLICATION_JSON);
                request.attribute("user", null);
            });
        });


        factory.buildDbContext();

        factory.buildAdminController();

        exception(AdminNotFoundException.class, factory.buildAdminNotFoundHandler());
        exception(ParseException.class, factory.buildParseHandler());

        exception(Exception.class, factory.buildExceptionHandler());
    }
}
