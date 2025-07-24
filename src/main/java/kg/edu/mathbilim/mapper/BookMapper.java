package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface BookMapper extends BaseMapper<Book, BookDto> {
    Book toEntity(BookDto dto);

    @Mapping(source = "creator", target = "creator")
    @Mapping(source = "mainImage", target = "mainImage")
    @Mapping(target = "id", source = "id")
    BookDto toDto(Book book);
}
