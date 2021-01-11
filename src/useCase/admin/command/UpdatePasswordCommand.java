package useCase.admin.command;

public class UpdatePasswordCommand {
    public Long id;
    public String password;

    public UpdatePasswordCommand(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
