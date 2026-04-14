package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.data.domain.Pageable;
import rus.cheremisin.itktasksspringmvc.DTO.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks(Pageable pageable);
    BookDTO addBook(BookDTO dto);
    BookDTO getBookById(Long id);
    BookDTO getBookByTitle(String title);
    BookDTO editBook(Long id, BookDTO dto);
    void deleteBook(Long id);

}
