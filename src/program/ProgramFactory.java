package program;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import exception.AdminNotFoundException;
import exception.handler.AdminNotFoundHandler;
import exception.handler.ParseHandler;
import repository.JSONDbContext;
import service.AdminService;
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


    private static ProgramFactory instance;

    // Json reader/writer for communication with frontend
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("dd.MM.yyyy. hh:mm:ss")
            .create();

    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. hh:mm:ss");

    private JSONDbContext jsonDbContext;

    private AdminService adminService;

    private AdminController adminController;

    private ExceptionHandler adminNotFoundHandler;
    private ExceptionHandler parseHandler;
    private ExceptionHandler exceptionHandler;

    private ProgramFactory() {

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
                    jsonDbContext.getAdminRepository()
            );
            adminController = new AdminController(
                    gson,
                    formatter,
                    adminService,
                    adminService);
        }
        return adminController;
    }

    public ExceptionHandler buildAdminNotFoundHandler() {
        if (adminNotFoundHandler == null)
            adminNotFoundHandler = new AdminNotFoundHandler(AdminNotFoundException.class);
        return adminNotFoundHandler;
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
