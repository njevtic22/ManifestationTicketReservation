package utility;

import exception.InvalidRoleException;
import spark.Request;

@FunctionalInterface
public interface RoleEnsure {
    void ensure(Request request) throws InvalidRoleException;
}
