package program;

import exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.path;
import static spark.Spark.port;
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

        factory.buildAuthenticationController();
        factory.buildAdminController();
        factory.buildSalesmanController();
        factory.buildCustomerController();
        factory.buildUserController();
        factory.buildHistoryController();
        factory.buildManifestationController();
        factory.buildReviewController();
        factory.buildTicketController();

        exception(UserNotFoundException.class, factory.buildUserNotFoundHandler());
        exception(AdminNotFoundException.class, factory.buildAdminNotFoundHandler());
        exception(SalesmanNotFoundException.class, factory.buildSalesmanNotFoundHandler());
        exception(CustomerNotFoundException.class, factory.buildCustomerNotFoundHandler());
        exception(ManifestationNotFoundException.class, factory.buildManifestationNotFoundHandler());
        exception(TicketNotFoundException.class, factory.buildTicketNotFoundHandler());
        exception(TicketReservedException.class, factory.buildTicketReservedHandler());
        exception(ReviewNotFoundException.class, factory.buildReviewNotFoundHandler());
        exception(UserNameTakenException.class, factory.buildUserNameTakenHandler());
        exception(ConstraintViolationException.class, factory.buildConstraintViolationHandler());
        exception(InvalidRoleException.class, factory.buildInvalidRoleHandler());
        exception(PlaceAndDateTakenException.class, factory.buildPlaceAndDateTakenHandler());
        exception(SoldOutException.class, factory.buildSoldOutHandler());

        exception(ExpiredJwtException.class, factory.buildExpiredJwtHandler());
        exception(SignatureException.class, factory.buildSignatureHandler());
        exception(TokenNotFoundException.class, factory.buildTokenNotFoundHandler());

        exception(IllegalArgumentException.class, factory.buildIllegalArgumentHandler());
        exception(ParseException.class, factory.buildParseHandler());

        exception(Exception.class, factory.buildExceptionHandler());
    }
}
