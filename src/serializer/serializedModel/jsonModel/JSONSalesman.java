package serializer.serializedModel.jsonModel;

import model.Gender;

import java.util.Date;
import java.util.List;

public class JSONSalesman {
    public long id;
    public String name;
    public String surname;
    public String username;
    public String password;
    public Date dateOfBirth;
    public Gender gender;
    public boolean archived;
    public List<Long> manifestationIds;
}
