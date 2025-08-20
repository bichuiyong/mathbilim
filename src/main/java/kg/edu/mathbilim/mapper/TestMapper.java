package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.test.TestDto;
import kg.edu.mathbilim.model.test.Test;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {

    TestDto toDto(Test test);
}
