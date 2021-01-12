package exception.handler;

import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

public class IllegalArgumentHandler extends ExceptionHandlerImpl {
    public IllegalArgumentHandler(Class<? extends Exception> exceptionClass) {
        super(exceptionClass);
    }

    @Override
    public void handle(Exception e, Request request, Response response) {
        e.printStackTrace();
        response.status(HttpStatus.BAD_REQUEST_400);

        String message = e.getMessage();
        if (e.getMessage().startsWith("No enum constant model.Gender"))
            message = "Invalid gender type. Allowed genders are MALE and FEMALE.";

        response.body(message);
    }
}
