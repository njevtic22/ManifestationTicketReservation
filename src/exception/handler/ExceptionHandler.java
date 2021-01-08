package exception.handler;

import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

public class ExceptionHandler extends ExceptionHandlerImpl {
    public ExceptionHandler(Class<? extends Exception> exceptionClass) {
        super(exceptionClass);
    }

    @Override
    public void handle(Exception e, Request request, Response response) {
        e.printStackTrace();
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        response.body(e.getMessage());
    }
}
