package rus.cheremisin.itktasksspringmvc.mapper;

import org.mapstruct.Mapper;
import rus.cheremisin.itktasksspringmvc.DTO.BookDTO;
import rus.cheremisin.itktasksspringmvc.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDto(Book book);
    Book toBook(BookDTO dto);
    List<BookDTO> toDtoList(List<Book> books);
}
