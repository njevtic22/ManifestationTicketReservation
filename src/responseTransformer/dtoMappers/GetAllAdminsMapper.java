package responseTransformer.dtoMappers;

import model.Admin;
import useCase.admin.dto.GetAllAdminsDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllAdminsMapper implements DTOMapper<Admin, GetAllAdminsDTO> {
    private final SimpleDateFormat formatter;

    public GetAllAdminsMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllAdminsDTO toDTO(Admin admin) {
        return new GetAllAdminsDTO(admin, formatter.format(admin.getDateOfBirth()));
    }

    @Override
    public List<GetAllAdminsDTO> toDTOList(Collection<Admin> admins) {
        return admins
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
