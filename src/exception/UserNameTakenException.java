package exception;

public class UserNameTakenException extends RuntimeException {
    public UserNameTakenException(String userName) {
        super("Username " + userName + " is taken.");
    }

    public UserNameTakenException(String userName, Throwable cause) {
        super("Username " + userName + " is taken.", cause);
    }
}
