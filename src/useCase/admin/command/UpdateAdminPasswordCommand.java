package useCase.admin.command;

public class UpdateAdminPasswordCommand {
    public Long id;
    public String password;

    public UpdateAdminPasswordCommand(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
