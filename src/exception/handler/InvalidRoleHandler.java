package exception.handler;

import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

public class InvalidRoleHandler extends ExceptionHandlerImpl {
    public InvalidRoleHandler(Class<? extends Exception> exceptionClass) {
        super(exceptionClass);
    }

    @Override
    public void handle(Exception e, Request request, Response response) {
        e.printStackTrace();
        response.status(HttpStatus.FORBIDDEN_403);
        response.body(e.getMessage());

        response.header("redirect", "/403");
    }
}
