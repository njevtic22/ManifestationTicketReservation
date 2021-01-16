package serializer.serializedModel.jsonModel;

import java.util.Date;
import java.util.List;

public class JSONCustomer {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String password;
    public Date dateOfBirth;
    public String gender;
    public boolean archived;

    public double points;
    public String type;
    public List<Long> tickets;
    public List<Long> withdrawalHistory;
}
