package serializer.deserializer;

import java.io.FileNotFoundException;

public interface FileDeserializer {
    void loadAdmins() throws FileNotFoundException;
    void loadSalesmen() throws FileNotFoundException;
    void loadCustomers() throws FileNotFoundException;
    void loadTickets() throws FileNotFoundException;
    void loadManifestations() throws FileNotFoundException;
    void loadWithdrawalHistory() throws FileNotFoundException;
    void loadReviews() throws FileNotFoundException;
    void buildReferences();
}
