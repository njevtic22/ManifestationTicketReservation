package useCase.salesman.command;

public class UpdateSalesmanPasswordCommand {
    public Long id;
    public String password;

    public UpdateSalesmanPasswordCommand(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
