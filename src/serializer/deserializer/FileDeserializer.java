package serializer.deserializer;

import java.io.FileNotFoundException;

public interface FileDeserializer {
    void loadAdmins() throws FileNotFoundException;
    void loadSalesmen() throws FileNotFoundException;
    void buildReferences();
}
