package program;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.AuthenticationController;
import controller.CustomerController;
import controller.ManifestationController;
import controller.ReviewController;
import controller.SalesmanController;
import controller.TicketController;
import exception.*;
import exception.handler.*;
import io.jsonwebtoken.ExpiredJwtException;
import repository.JSONDbContext;
import security.NoOpPasswordEncoder;
import security.TokenUtils;
import service.AdminService;
import service.AuthenticationService;
import service.CustomerService;
import service.ManifestationService;
import service.ReviewService;
import service.SalesmanService;
import service.TicketService;
import spark.ExceptionHandler;

import java.security.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProgramFactory {
    // Paths to folders and files
    public static final String WORKING_DIR = "."; /*System.getProperty("user.dir");*/
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String FILES_PATH = WORKING_DIR + FILE_SEPARATOR + "files";

    // Content types
    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";
    public static final String TEXT_PLAIN = "text/plain; charset=UTF-8";

    // Date format
    public static final String DATE_FORMAT = "dd.MM.yyyy. hh:mm:ss";

    private static ProgramFactory instance;

    // Json reader/writer for communication with frontend
    private Gson gson = new GsonBuilder()
//            .setPrettyPrinting()
            .setDateFormat(DATE_FORMAT)
            .create();

    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    private JSONDbContext jsonDbContext;

    private AuthenticationService authenticationService;
    private AdminService adminService;
    private SalesmanService salesmanService;
    private CustomerService customerService;
    private ManifestationService manifestationService;
    private ReviewController reviewController;
    private TicketController ticketController;

    private AuthenticationController authenticationController;
    private AdminController adminController;
    private SalesmanController salesmanController;
    private CustomerController customerController;
    private ManifestationController manifestationController;
    private ReviewService reviewService;
    private TicketService ticketService;

    private ExceptionHandler adminNotFoundHandler;
    private ExceptionHandler salesmanNotFoundHandler;
    private ExceptionHandler customerNotFoundHandler;
    private ExceptionHandler manifestationNotFoundHandler;
    private ExceptionHandler ticketNotFoundHandler;
    private ExceptionHandler ticketReservedHandler;
    private ExceptionHandler userNameTakenHandler;
    private ExceptionHandler constraintViolationHandler;
    private ExceptionHandler invalidRoleHandler;
    private ExceptionHandler placeAndDateTakenHandler;

    private ExceptionHandler expiredJwtHandler;
    private ExceptionHandler signatureHandler;
    private ExceptionHandler tokenNotFoundHandler;

    private ExceptionHandler illegalArgumentHandler;
    private ExceptionHandler parseHandler;

    private ExceptionHandler exceptionHandler;

    private ProgramFactory() {
        formatter.setLenient(false);
    }

    public static ProgramFactory buildFactory() {
        if (instance == null)
            instance = new ProgramFactory();
        return instance;
    }

    public JSONDbContext buildDbContext() {
        if (jsonDbContext == null)
            jsonDbContext = new JSONDbContext(gson, ProgramFactory.FILES_PATH, ProgramFactory.FILE_SEPARATOR);
        return jsonDbContext;
    }

    public AuthenticationController buildAuthenticationController() {
        if (authenticationController == null) {
            authenticationService = new AuthenticationService(
                    formatter,
                    new TokenUtils(),
                    new NoOpPasswordEncoder(),
                    jsonDbContext.getAuthenticationRepository()
            );
            authenticationController = new AuthenticationController(
                    gson,
                    authenticationService,
                    authenticationService,
                    authenticationService,
                    authenticationService
            );
        }
        return authenticationController;
    }

    public AdminController buildAdminController() {
        if (adminController == null) {
            adminService = new AdminService(
                    formatter,
                    jsonDbContext.getAdminRepository(),
                    jsonDbContext.getSalesmanRepository(),
                    jsonDbContext.getCustomerRepository());
            adminController = new AdminController(
                    gson,
                    formatter,
                    adminService,
                    adminService,
                    adminService,
                    adminService,
                    adminService,
                    adminService
            );
        }
        return adminController;
    }

    public SalesmanController buildSalesmanController() {
        if (salesmanController == null) {
            salesmanService = new SalesmanService(
                    formatter,
                    jsonDbContext.getSalesmanRepository(),
                    jsonDbContext.getAdminRepository(),
                    jsonDbContext.getCustomerRepository());
            salesmanController = new SalesmanController(
                    gson,
                    formatter,
                    salesmanService,
                    salesmanService,
                    salesmanService,
                    salesmanService,
                    salesmanService,
                    salesmanService
            );
        }
        return salesmanController;
    }

    public CustomerController buildCustomerController() {
        if (customerController == null) {
            customerService = new CustomerService(
                    formatter,
                    jsonDbContext.getCustomerRepository(),
                    jsonDbContext.getSalesmanRepository(),
                    jsonDbContext.getAdminRepository()
            );
            customerController = new CustomerController(
                    gson,
                    formatter,
                    customerService,
                    customerService,
                    customerService,
                    customerService,
                    customerService,
                    customerService);
        }
        return customerController;
    }

    public ManifestationController buildManifestationController() {
        if (manifestationController == null) {
            manifestationService = new ManifestationService(
                    formatter,
                    jsonDbContext.getManifestationRepository(),
                    jsonDbContext.getTicketRepository(),
                    jsonDbContext.getSalesmanRepository()
            );
            manifestationController = new ManifestationController(
                    gson,
                    formatter,
                    manifestationService,
                    manifestationService,
                    manifestationService,
                    manifestationService,
                    manifestationService,
                    manifestationService
            );
        }
        return manifestationController;
    }

    public ReviewController buildReviewController() {
        if (reviewController == null) {
            reviewService = new ReviewService(

            );
            reviewController = new ReviewController(

            );
        }
        return reviewController;
    }

    public TicketController buildTicketController() {
        if (ticketController == null) {
            ticketService = new TicketService(
                    formatter,
                    jsonDbContext.getTicketRepository(),
                    jsonDbContext.getManifestationRepository(),
                    jsonDbContext.getHistoryRepository(),
                    jsonDbContext.getCustomerRepository()
            );
            ticketController = new TicketController(
                    gson, formatter,
                    ticketService,
                    ticketService,
                    ticketService,
                    ticketService
            );
        }
        return ticketController;
    }

    public ExceptionHandler buildAdminNotFoundHandler() {
        if (adminNotFoundHandler == null)
            adminNotFoundHandler = new AdminNotFoundHandler(AdminNotFoundException.class);
        return adminNotFoundHandler;
    }

    public ExceptionHandler buildSalesmanNotFoundHandler() {
        if (salesmanNotFoundHandler == null)
            salesmanNotFoundHandler = new SalesmanNotFoundHandler(SalesmanNotFoundException.class);
        return salesmanNotFoundHandler;
    }

    public ExceptionHandler buildCustomerNotFoundHandler() {
        if (customerNotFoundHandler == null)
            customerNotFoundHandler = new CustomerNotFoundHandler(CustomerNotFoundException.class);
        return customerNotFoundHandler;
    }

    public ExceptionHandler buildManifestationNotFoundHandler() {
        if (manifestationNotFoundHandler == null)
            manifestationNotFoundHandler = new ManifestationNotFoundHandler(ManifestationNotFoundException.class);
        return manifestationNotFoundHandler;
    }

    public ExceptionHandler buildTicketNotFoundHandler() {
        if (ticketNotFoundHandler == null)
            ticketNotFoundHandler = new TicketNotFoundHandler(TicketNotFoundException.class);
        return ticketNotFoundHandler;
    }

    public ExceptionHandler buildTicketReservedHandler() {
        if (ticketReservedHandler == null)
            ticketReservedHandler = new TicketReservedHandler(TicketReservedException.class);
        return ticketReservedHandler;
    }

    public ExceptionHandler buildUserNameTakenHandler() {
        if (userNameTakenHandler == null)
            userNameTakenHandler = new UserNameTakenHandler(UserNameTakenException.class);
        return userNameTakenHandler;
    }

    public ExceptionHandler buildConstraintViolationHandler() {
        if (constraintViolationHandler == null)
            constraintViolationHandler = new ConstraintViolationHandler(ConstraintViolationException.class);
        return constraintViolationHandler;
    }

    public ExceptionHandler buildInvalidRoleHandler() {
        if (invalidRoleHandler == null)
            invalidRoleHandler = new InvalidRoleHandler(InvalidRoleException.class);
        return invalidRoleHandler;
    }

    public ExceptionHandler buildPlaceAndDateTakenHandler() {
        if (placeAndDateTakenHandler == null)
            placeAndDateTakenHandler = new PlaceAndDateTakenHandler(PlaceAndDateTakenException.class);
        return placeAndDateTakenHandler;
    }

    public ExceptionHandler buildExpiredJwtHandler() {
        if (expiredJwtHandler == null)
            expiredJwtHandler = new ExpiredJwtHandler(ExpiredJwtException.class);
        return expiredJwtHandler;
    }

    public ExceptionHandler buildSignatureHandler() {
        if (signatureHandler == null)
            signatureHandler = new SignatureHandler(SignatureException.class);
        return signatureHandler;
    }

    public ExceptionHandler buildTokenNotFoundHandler() {
        if (tokenNotFoundHandler == null)
            tokenNotFoundHandler = new TokenNotFoundHandler(TokenNotFoundException.class);
        return tokenNotFoundHandler;
    }

    public ExceptionHandler buildIllegalArgumentHandler() {
        if (illegalArgumentHandler == null)
            illegalArgumentHandler = new IllegalArgumentHandler(IllegalArgumentException.class);
        return illegalArgumentHandler;
    }

    public ExceptionHandler buildParseHandler() {
        if (parseHandler == null)
            parseHandler = new ParseHandler(ParseException.class);
        return parseHandler;
    }

    public ExceptionHandler buildExceptionHandler() {
        if (exceptionHandler == null)
            exceptionHandler = new exception.handler.ExceptionHandler(Exception.class);
        return exceptionHandler;
    }
}