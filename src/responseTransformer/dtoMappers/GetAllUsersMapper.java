package responseTransformer.dtoMappers;

import model.User;
import useCase.user.dto.GetAllUsersDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllUsersMapper implements DTOMapper<User, GetAllUsersDTO> {
    private final SimpleDateFormat formatter;

    public GetAllUsersMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllUsersDTO toDTO(User user) {
        return new GetAllUsersDTO(user, formatter.format(user.getDateOfBirth()));
    }

    @Override
    public List<GetAllUsersDTO> toDTOList(Collection<User> users) {
        return users
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
