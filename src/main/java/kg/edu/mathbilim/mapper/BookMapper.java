package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper extends BaseMapper<Book, BookDto> {
    Book toEntity(BookDto dto);

    @Mapping(target = "id", source = "id")
    BookDto toDto(Book book);
}
