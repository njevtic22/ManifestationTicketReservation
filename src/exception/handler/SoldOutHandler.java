package exception.handler;

import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

public class SoldOutHandler extends ExceptionHandlerImpl {
    public SoldOutHandler(Class<? extends Exception> exceptionClass) {
        super(exceptionClass);
    }

    @Override
    public void handle(Exception e, Request request, Response response) {
        e.printStackTrace();
        response.status(HttpStatus.BAD_REQUEST_400);
        response.body(e.getMessage());
    }
}
