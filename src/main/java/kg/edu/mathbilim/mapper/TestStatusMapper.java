package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.TestStatusDto;
import kg.edu.mathbilim.model.TestStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestStatusMapper {
    TestStatus toEntity(TestStatusDto dto);

    TestStatusDto toDto(TestStatus testStatus);
}
