package rus.cheremisin.itktasksspringmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.DTO.BookDTO;
import rus.cheremisin.itktasksspringmvc.dao.AuthorDAO;
import rus.cheremisin.itktasksspringmvc.dao.BookDAO;
import rus.cheremisin.itktasksspringmvc.entity.Author;
import rus.cheremisin.itktasksspringmvc.entity.Book;
import rus.cheremisin.itktasksspringmvc.enums.Genre;
import rus.cheremisin.itktasksspringmvc.exceptions.AuthorNotFoundException;
import rus.cheremisin.itktasksspringmvc.exceptions.BookNotFoundException;
import rus.cheremisin.itktasksspringmvc.exceptions.GenreListIsNullException;
import rus.cheremisin.itktasksspringmvc.mapper.BookMapper;
import rus.cheremisin.itktasksspringmvc.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final BookMapper bookMapper;
    private final AuthorDAO authorDAO;
    @Autowired
    public BookServiceImpl(BookDAO bookDAO, BookMapper bookMapper, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
        this.authorDAO = authorDAO;
    }

    @Override
    public List<BookDTO> getAllBooks(Pageable pageable) {
        if (pageable == null) {
            throw new NullPointerException("pageable is null!");
        }
        return bookMapper.toDtoList(bookDAO.findAll(pageable).toList());
    }

    @Override
    public BookDTO addBook(BookDTO dto) {
        if (dto == null) {
            throw new NullPointerException("cannot add null book");
        }
        Book newBook = bookMapper.toBook(dto);
        return bookMapper.toDto(bookDAO.save(newBook));
    }

    @Override
    public BookDTO getBookById(Long id) {
        if (id == null) {
            throw new NullPointerException("check title, it cannot be null");
        }
        Optional<Book> authorOptional = bookDAO.findById(id);
        if (authorOptional.isEmpty()) {
            throw new BookNotFoundException("no book by that ID is found");
        }
        return bookMapper.toDto(authorOptional.get());
    }

    @Override
    public BookDTO getBookByTitle(String title) {
        if (title == null) {
            throw new NullPointerException("check title, it cannot be null");
        }
        Optional<Book> authorOptional = bookDAO.findBookByTitle(title);
        if (authorOptional.isEmpty()) {
            throw new BookNotFoundException("no book by that title is found");
        }
        return bookMapper.toDto(authorOptional.get());
    }

    @Override
    public BookDTO editBook(Long id, BookDTO dto) {
        Optional<Book> bookOptional = bookDAO.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("no book with such ID is found");
        }
        Book existingBook = bookOptional.get();
        if (dto != null) {
            Optional<Author> author = authorDAO.findAuthorByLastNameAndFirstName(dto.author().lastName(), dto.author().firstName());
            if (author.isEmpty()) {
                throw new AuthorNotFoundException("no author by that name is found");
            }

            existingBook.setTitle(dto.title());
            existingBook.setAuthor(author.get());
            existingBook.setPublishingYear(dto.publishingYear());
            List<Genre> updatedGenres = dto.genres();
            if (updatedGenres != null) {
                existingBook.setGenres(updatedGenres);
            } else {
                throw new GenreListIsNullException("Genre list is null!");
            }
            return bookMapper.toDto(bookDAO.save(existingBook));
        } else {
            throw new NullPointerException("cannot update from null book");
        }
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book> bookOptional = bookDAO.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("no book with such ID");
        }
        bookDAO.delete(bookOptional.get());
    }
}