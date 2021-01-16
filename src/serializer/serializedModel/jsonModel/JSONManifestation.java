package serializer.serializedModel.jsonModel;

import java.util.Date;
import java.util.List;

public class JSONManifestation {
    public long id;
    public String name;
    public long numberOfTicketsLeft;
    public double regularTicketPrice;
    public Date holdingDate;
    public String status;
    public String type;
    public boolean archived;

    public JSONLocation location;
    public JSONImage image;
    public List<Long> tickets;
}
