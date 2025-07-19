package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.ResultDto;
import kg.edu.mathbilim.model.Result;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResultMapper {

    ResultDto toDto(Result result);

    Result toEntity(ResultDto dto);
}
