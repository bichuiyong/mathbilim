package kg.edu.mathbilim.mapper.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import kg.edu.mathbilim.dto.user.user_type.UserTypeDto;

@Component
public class StringToUserTypeDtoConverter implements Converter<String, UserTypeDto> {

    @Override
    public UserTypeDto convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return UserTypeDto.builder()
                .id(Integer.parseInt(source))
                .build();
    }
}

