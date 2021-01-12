package program;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.SalesmanController;
import exception.AdminNotFoundException;
import exception.ConstraintViolationException;
import exception.SalesmanNotFoundException;
import exception.UserNameTakenException;
import exception.handler.AdminNotFoundHandler;
import exception.handler.ConstraintViolationHandler;
import exception.handler.IllegalArgumentHandler;
import exception.handler.ParseHandler;
import exception.handler.SalesmanNotFoundHandler;
import exception.handler.UserNameTakenHandler;
import repository.JSONDbContext;
import service.AdminService;
import service.SalesmanService;
import spark.ExceptionHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProgramFactory {
    // Paths to folders and files
    public static final String workingDir = "."; /*System.getProperty("user.dir");*/
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String filesPath = workingDir + fileSeparator + "files";

    // Content types
    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";
    public static final String TEXT_PLAIN = "text/plain; charset=UTF-8";

    // Date format
    public static String dateFormat = "dd.MM.yyyy. hh:mm:ss";

    private static ProgramFactory instance;

    // Json reader/writer for communication with frontend
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat(dateFormat)
            .create();

    private SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    private JSONDbContext jsonDbContext;

    private AdminService adminService;
    private SalesmanService salesmanService;

    private AdminController adminController;
    private SalesmanController salesmanController;

    private ExceptionHandler adminNotFoundHandler;
    private ExceptionHandler salesmanNotFoundHandler;
    private ExceptionHandler userNameTakenHandler;
    private ExceptionHandler constraintViolationHandler;

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
            jsonDbContext = new JSONDbContext(gson, ProgramFactory.filesPath, ProgramFactory.fileSeparator);
        return jsonDbContext;
    }

    public AdminController buildAdminController() {
        if (adminController == null) {
            adminService = new AdminService(
                    formatter,
                    jsonDbContext.getAdminRepository(),
                    jsonDbContext.getSalesmanRepository()
            );
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
                    jsonDbContext.getAdminRepository()
            );
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
