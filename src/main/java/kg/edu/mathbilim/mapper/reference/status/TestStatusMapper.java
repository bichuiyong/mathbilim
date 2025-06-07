package kg.edu.mathbilim.mapper.reference.status;

import kg.edu.mathbilim.dto.reference.status.TestStatusDto;
import kg.edu.mathbilim.model.reference.status.TestStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestStatusMapper {
    TestStatus toEntity(TestStatusDto dto);

    TestStatusDto toDto(TestStatus testStatus);
}
