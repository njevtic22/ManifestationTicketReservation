package serializer.serializer;

import java.util.Map;

public interface FileSerializer<T, ID> {
    void save(Map<ID, T> data);
}
