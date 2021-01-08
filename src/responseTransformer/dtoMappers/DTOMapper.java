package responseTransformer.dtoMappers;

import java.util.Collection;
import java.util.List;

public interface DTOMapper<T, DTO> {
    DTO toDTO(T t);
    List<DTO> toDTOList(Collection<T> tList);
}
