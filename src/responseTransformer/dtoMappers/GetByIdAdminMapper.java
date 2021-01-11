package responseTransformer.dtoMappers;

import model.Admin;
import useCase.admin.dto.GetByIdAdminDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetByIdAdminMapper implements DTOMapper<Admin, GetByIdAdminDTO> {
    private final SimpleDateFormat formatter;

    public GetByIdAdminMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetByIdAdminDTO toDTO(Admin admin) {
        return new GetByIdAdminDTO(admin, formatter.format(admin.getDateOfBirth()));
    }

    @Override
    public List<GetByIdAdminDTO> toDTOList(Collection<Admin> admins) {
        return admins
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
