package exception.handler;

import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

public class SalesmanNotFoundHandler extends ExceptionHandlerImpl {
    public SalesmanNotFoundHandler(Class<? extends Exception> exceptionClass) {
        super(exceptionClass);
    }

    @Override
    public void handle(Exception e, Request request, Response response) {
        e.printStackTrace();
        response.status(HttpStatus.NOT_FOUND_404);
        response.body(e.getMessage());
    }
}
