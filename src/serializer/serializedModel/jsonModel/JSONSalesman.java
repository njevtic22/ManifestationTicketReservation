package serializer.serializedModel.jsonModel;

import java.util.Date;
import java.util.List;

public class JSONSalesman {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String password;
    public Date dateOfBirth;
    public String gender;
    public boolean archived;
    public List<Long> manifestations;
}
