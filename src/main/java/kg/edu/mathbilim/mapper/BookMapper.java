package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends BaseMapper<Book, BookDto> {
    Book toEntity(BookDto dto);

    BookDto toDto(Book book);
}
