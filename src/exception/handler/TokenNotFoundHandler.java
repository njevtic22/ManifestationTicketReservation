package exception.handler;

import org.eclipse.jetty.http.HttpStatus;
import program.ProgramFactory;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

public class TokenNotFoundHandler extends ExceptionHandlerImpl {
    public TokenNotFoundHandler(Class<? extends Exception> exceptionClass) {
        super(exceptionClass);
    }

    @Override
    public void handle(Exception e, Request request, Response response) {
        e.printStackTrace();
        response.status(HttpStatus.TEMPORARY_REDIRECT_307);
        response.body(e.getMessage());

        response.header("redirect", "/login");
    }
}
